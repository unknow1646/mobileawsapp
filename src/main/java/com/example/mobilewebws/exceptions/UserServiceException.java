package com.example.mobilewebws.exceptions;

public class UserServiceException extends RuntimeException{

  private static final long serialVersionUID = 1348771109171435607L;

  public UserServiceException(String message) {
    super(message);
  }

}
