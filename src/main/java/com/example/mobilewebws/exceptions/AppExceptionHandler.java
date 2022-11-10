package com.example.mobilewebws.exceptions;

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

import com.example.mobilewebws.ui.response.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = UserServiceException.class)
  public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request){
    ErrorMessage errorMessages = new ErrorMessage(new Date(),ex.getMessage());
    return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest request){
    ErrorMessage errorMessages = new ErrorMessage(new Date(),ex.getMessage());
    return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = IdNotFoundException.class)
  public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException ex, WebRequest request){
    ErrorMessage errorMessages = new ErrorMessage(new Date(),ex.getMessage());
    return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = SearchNamesNotFoundException.class)
  public ResponseEntity<Object> handleSearNamesNotFoundException(SearchNamesNotFoundException ex, WebRequest request){
    ErrorMessage errorMessages = new ErrorMessage(new Date(),ex.getMessage());
    return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }
}
