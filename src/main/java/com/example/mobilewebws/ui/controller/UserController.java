package com.example.mobilewebws.ui.controller;

import com.example.mobilewebws.exceptions.IdNotFoundException;
import com.example.mobilewebws.exceptions.SearchNamesNotFoundException;
import com.example.mobilewebws.exceptions.UserServiceException;
import com.example.mobilewebws.service.UserService;
import com.example.mobilewebws.servicebean.BeanService;
import com.example.mobilewebws.shared.dto.UserDto;
import com.example.mobilewebws.ui.model.request.UserDetailsRequestModel;
import com.example.mobilewebws.ui.response.UserRest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
public class UserController implements BatchRequest {

  UserService userService;

  @Autowired
  @Qualifier("A")
  BeanService beanServiceA;

  @Autowired
  @Qualifier("B")
  BeanService beanServiceB;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @Override
  public ResponseEntity<UserRest> getUser(@PathVariable("userId") String userId) {

    UserRest response = new UserRest();
    UserDto userById = userService.getUserById(userId);
    if (Objects.isNull(userById)) throw new UserServiceException("USER NOT FOUND");
    System.out.println(userById);
    BeanUtils.copyProperties(userById, response);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public ResponseEntity<Long> getUserId(@PathVariable("firstName") String firstName){
    Long userIdByName = userService.getUserIdByName(firstName);
    return ResponseEntity.status(HttpStatus.OK).body(userIdByName);
  }

  @Override
  public ResponseEntity<List<UserRest>> getUsersFirstName(@PathVariable("firstName") String firstName){

    List<UserDto> usersByFirstName = userService.getUsersByFirstName(firstName);
    if(usersByFirstName.isEmpty())
      throw new SearchNamesNotFoundException("NAME SEARCH NOT FOUND");

    return ResponseEntity.status(HttpStatus.OK).body(
        usersByFirstName.stream().map(
            userDto -> {
              List<UserRest> userRestList = new ArrayList<>();
              userRestList.add(UserRest.builder()
                  .firstName(userDto.getFirstName())
                  .lastName(userDto.getLastName())
                  .email(userDto.getEmail())
                  .userId(userDto.getUserId())
                  .build());
              return userRestList;
            }
        ).flatMap(List::stream).collect(Collectors.toList()));
  }

  @Override
  @GetMapping(path = "/beanA")
  public ResponseEntity<String> getBeanA(){
    beanServiceA.startBean();
    beanServiceA.stopBean();
    return ResponseEntity.status(HttpStatus.OK).body("Finalizado Bean A");
  }

  @Override
  @GetMapping(path = "/beanB")
  public ResponseEntity<String> getBeanB(){
    beanServiceB.startBean();
    beanServiceB.stopBean();
    return ResponseEntity.status(HttpStatus.OK).body("Finalizado Bean B");
  }

  @Override
  public ResponseEntity<List<UserRest>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "25") int limit) {
    return ResponseEntity.status(HttpStatus.OK)
    .body(userService.getUsers(page, limit).stream().map(userDto -> {
      List<UserRest> response = new ArrayList<>();
      response.add(UserRest
          .builder()
          .userId(userDto.getUserId())
          .firstName(userDto.getFirstName())
          .lastName(userDto.getLastName())
          .email(userDto.getEmail())
          .build());
      return response;
    }).flatMap(Collection::stream)
        .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<UserRest> registerUser(@RequestBody @Validated UserDetailsRequestModel userDetailsRequestModel) {

    UserRest returnValue = new UserRest();
    UserDto userDto = new UserDto();

    if(userDetailsRequestModel.getFirstName().isEmpty()){
      throw new NullPointerException("The field first name is null");
    }
          BeanUtils.copyProperties(userDetailsRequestModel, userDto);

    System.out.println("uSER dto");
    System.out.println(userDto);

    UserDto createdUser = userService.createUser(userDto);
    System.out.println("created user");
    System.out.println(createdUser);
    BeanUtils.copyProperties(createdUser, returnValue);
    System.out.println("return value");
    System.out.println(returnValue);

    return ResponseEntity.status(HttpStatus.OK).body(returnValue);
  }

  @Override
  public void updateUser(@PathVariable("userId") String userId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email){

    UserDto userById = userService.getUserById(userId);
    if(Objects.isNull(userById)) throw new IdNotFoundException("USER DOESN'T EXIST");
    userService.updateUser(userId,name, email);
  }

  @Override
  public void deleteUser(@PathVariable("userId") String userId){
    userService.deleteUser(userId);
  }
}
