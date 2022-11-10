package com.example.mobilewebws.internal.queryservice;

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

import com.example.mobilewebws.exceptions.IdNotFoundException;
import com.example.mobilewebws.io.entity.UserEntity;
import com.example.mobilewebws.ui.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalQueryServiceImpl implements InternalQuery{

  UserRepository userRepository;

  @Autowired
  public InternalQueryServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public Long getIdByName(String userName) {
    List<UserEntity> byFirstName = userRepository.findByFirstName(userName);
    if(byFirstName.isEmpty()){
      throw new IdNotFoundException("USER ID NOT FOUND ");
    }
    return byFirstName.get(0).getId();
  }
}
