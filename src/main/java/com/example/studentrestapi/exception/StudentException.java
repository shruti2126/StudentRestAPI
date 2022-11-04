package com.example.studentrestapi.exception;

public class StudentException extends RuntimeException {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public StudentException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public StudentException() {
        super();
    }
}
