package com.example.studentrestapi.exception;

public class StudentNotFoundException extends StudentException{
    public StudentNotFoundException() {
        super("STUDENT_NOT_FOUND");
    }
}
