package com.example.studentrestapi.exception;

public class StudentNameIsNullException extends StudentException {
    public StudentNameIsNullException() {
        super("name of student in request-body is provided as null");
    }
}
