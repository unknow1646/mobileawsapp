package com.example.mobilewebws.exceptions;

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
