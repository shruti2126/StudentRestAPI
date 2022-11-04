package com.example.studentrestapi.controller;

import com.example.studentrestapi.exception.EmailAddressInvalidException;
import com.example.studentrestapi.exception.StudentNameIsNullException;
import com.example.studentrestapi.exception.StudentNotFoundException;
import com.example.studentrestapi.vo.ErrorResponse;
import com.example.studentrestapi.vo.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentNameIsNullException.class)
    public ResponseEntity<ResponseMessage> nameNotProvided(Exception ex){
        ResponseMessage res = new ResponseMessage();
        res.setMessage(ex.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAddressInvalidException.class)
    public ResponseEntity<ErrorResponse> emailInvalid(Exception ex){
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}