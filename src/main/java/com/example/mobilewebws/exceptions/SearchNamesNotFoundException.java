package com.example.mobilewebws.exceptions;

public class SearchNamesNotFoundException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public SearchNamesNotFoundException(String message){
    super(message);
  }
}
