package com.example.mobilewebws.ui.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserRest {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;

  public UserRest() {
  }
}

