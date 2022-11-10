package com.example.mobilewebws.ui.controller;

import com.example.mobilewebws.ui.model.request.UserDetailsRequestModel;
import com.example.mobilewebws.ui.response.UserRest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
public interface BatchRequest {

  @GetMapping(path = "/{userId}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<UserRest> getUser(@PathVariable("userId") String userId);

  @GetMapping(path = "/firstName/{firstName}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<List<UserRest>> getUsersFirstName(@PathVariable("firstName") String firstName);

  @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<UserRest> registerUser(@RequestBody @Validated UserDetailsRequestModel userDetailsRequestModel)
      throws Exception;

  @GetMapping
  ResponseEntity<List<UserRest>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "25") int limit);

  @PutMapping(path = "/edit/{userId}")
  void updateUser(@PathVariable("userId") String userId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email);

  @DeleteMapping(path = "/delete/{userId}")
  void deleteUser(@PathVariable("userId") String userId);

  @GetMapping(path = "/id/{firstName}", produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<Long> getUserId(@PathVariable("firstName") String firstName);

  @GetMapping(path = "/beanA")
  ResponseEntity<String> getBeanA();

  @GetMapping(path = "/beanB")
  ResponseEntity<String> getBeanB();
}
