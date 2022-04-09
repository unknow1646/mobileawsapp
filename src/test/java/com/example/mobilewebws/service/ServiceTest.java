package com.example.mobilewebws.service;

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

import com.example.mobilewebws.io.entity.UserEntity;
import com.example.mobilewebws.service.impl.UserServiceImpl;
import com.example.mobilewebws.shared.Utils;
import com.example.mobilewebws.shared.dto.UserDto;
import com.example.mobilewebws.ui.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Financing Service Test")
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

  UserService userService;
  Utils utils;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Mock
  UserRepository userRepository;

  @BeforeEach
  public void setup() {
     userService = new UserServiceImpl(userRepository, utils, bCryptPasswordEncoder);
  }


  @Test
  void test(){
    when(userRepository.findByFirstName(anyString())).thenReturn(List.of(UserEntity.builder()
        .userId("userId")
        .firstName("Hernan")
        .lastName("Diaz")
        .build()));
    List<UserDto> usersByFirstName = userService.getUsersByFirstName("Hernan");
    assertEquals("Hernan", usersByFirstName.get(0).getFirstName());
  }




}
