package com.example.mobilewebws.service;

import com.example.mobilewebws.shared.dto.UserDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDto);
  UserDto getUserById(String userId) ;
  UserDto getUser(String email);
  List<UserDto> getUsers(int page, int limit);
  List<UserDto> getUsersByFirstName(String firstName);
  void deleteUser(String userId);
  void updateUser(String userId, String name, String email);
  Long getUserIdByName(String username);

}
