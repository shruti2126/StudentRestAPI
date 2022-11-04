package com.example.studentrestapi.exception;

public class EmailAddressInvalidException extends RuntimeException{
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public EmailAddressInvalidException(){
        super();
    }
    public EmailAddressInvalidException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
