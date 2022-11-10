package com.example.mobilewebws.internal.queryservice;

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
