package com.example.mobilewebws.ui.controller;

/*
 * Copyright 2021 MonetaGo, Inc. All Rights Reserved.
 *
 * This code is copyrighted material that is confidential and proprietary to MonetaGo, Inc.
 * and may not (in whole or in part) be published, publicly displayed, copied, modified or
 * used in any way other than as expressly permitted in a written agreement executed by
 * MonetaGo, Inc. No portion of this code may be used to create derivative works or exploited
 * in any other way without MonetaGo, Inc.'s prior written consent. No portion of this code
 * may be transmitted or redistributed to any person without MonetaGo, Inc.'s prior written
 * consent. This notice may not be deleted or modified without MonetaGo, Inc.'s consent.
 */

import com.example.mobilewebws.service.UserService;
import com.example.mobilewebws.shared.dto.UserDto;
import com.example.mobilewebws.ui.model.request.UserDetailsRequestModel;
import com.example.mobilewebws.ui.response.UserRest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
public class UserController {

  UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping(path = "{userId}",
      produces = {MediaType.APPLICATION_XML_VALUE,
          MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<UserRest> getUser(@PathVariable("userId") String userId) {

    UserRest response = new UserRest();
    UserDto userById = userService.getUserById(userId);
    System.out.println(userById);
    BeanUtils.copyProperties(userById, response);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<List<UserRest>> getAllUsers() {

    return ResponseEntity.status(HttpStatus.OK)
    .body(userService.getUsers().stream().map(userDto -> {
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

  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<UserRest> registerUser(@RequestBody @Validated UserDetailsRequestModel userDetailsRequestModel){

    UserRest returnValue = new UserRest();
    UserDto userDto = new UserDto();
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

  @PutMapping(path = "{userId}")
  public void updateUser(@PathVariable("userId") String userId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email){

    userService.updateUser(userId,name, email);

  }

  @DeleteMapping(path = "{userId}")
  public void deleteUser(@PathVariable("userId") String userId){
    userService.deleteUser(userId);
  }

}
