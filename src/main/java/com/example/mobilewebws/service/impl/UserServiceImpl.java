package com.example.mobilewebws.service.impl;

import com.example.mobilewebws.exceptions.IdNotFoundException;
import com.example.mobilewebws.exceptions.UserServiceException;
import com.example.mobilewebws.internal.queryservice.InternalQuery;
import com.example.mobilewebws.io.entity.UserEntity;
import com.example.mobilewebws.service.UserService;
import com.example.mobilewebws.shared.Utils;
import com.example.mobilewebws.shared.dto.UserDto;
import com.example.mobilewebws.ui.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  InternalQuery internalQuery;
  Utils utils;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, Utils utils,
      BCryptPasswordEncoder bCryptPasswordEncoder, InternalQuery internalQuery) {
    this.userRepository = userRepository;
    this.utils = utils;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.internalQuery = internalQuery;
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    Optional<UserEntity> userByEmail = userRepository.findByEmail(userDto.getEmail());
    System.out.println("user by email " + userByEmail.toString());

    if (userByEmail.isPresent()) {
      throw new UserServiceException("Email already exist");
    }

    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(userDto, userEntity);

    System.out.println("SEGUNDO " + userEntity);

    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);

    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    UserEntity storedUserDatails = userRepository.save(userEntity);

    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(storedUserDatails, returnValue);
    return returnValue;
  }

  @Override
  public UserDto getUserById(String userId) {

    return userRepository.findByUserId(userId).
        map(userEntity -> {
          UserDto userDtox = new UserDto();
          BeanUtils.copyProperties(userEntity, userDtox);
          return userDtox;
        }).orElseThrow(() -> new IdNotFoundException("User Not Found"));
  }

  @Override
  public UserDto getUser(String email) {

    return userRepository.findByEmail(email).
        map(userEntity -> {
          UserDto userDto = new UserDto();
          BeanUtils.copyProperties(userEntity, userDto);
          return userDto;
        }).orElseThrow(() -> new UsernameNotFoundException(email));
  }

  @Override
  public List<UserDto> getUsers(int page, int limit) {

    Pageable pageableRequest = PageRequest.of(page, limit);

    return userRepository.findAll(pageableRequest).getContent().stream()
        .map(userEntity -> {
          List<UserDto> responseDto = new ArrayList<>(200);
          responseDto.add(UserDto.builder()
              .id(userEntity.getId())
              .userId(userEntity.getUserId())
              .firstName(userEntity.getFirstName())
              .lastName(userEntity.getLastName())
              .email(userEntity.getEmail())
              .encryptedPassword(userEntity.getEncryptedPassword())
              .build());
          return responseDto;
        }).flatMap(List::stream).collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUsersByFirstName(String firstName) {
    return userRepository.findByFirstName(firstName)
      .stream().map(userEntity -> {
    List<UserDto> userDtoList = new ArrayList<>();
    userDtoList.add(UserDto.builder()
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .userId(userEntity.getUserId())
        .email(userEntity.getEmail())
        .build());
    return userDtoList;
  }).flatMap(List::stream).collect(Collectors.toList());
}

  @Override
  @Transactional
  public void deleteUser(String userId) {
    userRepository.findByUserId(userId)
        .stream()
        .findFirst()
        .map(userEntity -> {
      userRepository.deleteByUserId(userEntity.getUserId());
      return userEntity;
    }).orElseThrow(() -> new RuntimeException("User Not Found"));
  }

  @Transactional
  @Override
  public void updateUser(String userId, String name, String email) {
    Optional<UserEntity> user = (userRepository.findByUserId(userId));

    if (user.isEmpty()) {
      throw new IdNotFoundException("User Not Found");
    }

    if (name != null && name.length() > 0 && !Objects.equals(user.get().getFirstName(), name)) {
      user.get().setFirstName(name);
    }

    if (email != null && email.length() > 0 && !Objects.equals(user.get().getEmail(), email)) {
      Optional<UserEntity> userByEmail = userRepository.findByEmail(email);
      if (userByEmail.isPresent()) {
        throw new IdNotFoundException("Email Taken");
      }
      user.get().setEmail(email);
    }
  }

  @Override
  public Long getUserIdByName(String username) {
    return internalQuery.getIdByName(username);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> userEntity = userRepository.findByEmail(email);
    if (userEntity.isEmpty()) {
      throw new IdNotFoundException(email + " Not Found");
    }
    return new User(userEntity.get().getEmail(), userEntity.get().getEncryptedPassword(),
        new ArrayList<>());
  }
}
