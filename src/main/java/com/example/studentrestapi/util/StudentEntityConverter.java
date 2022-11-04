package com.example.studentrestapi.util;


import com.example.studentrestapi.entity.StudentEntity;
import com.example.studentrestapi.vo.Student;

public class StudentEntityConverter {
    public static Student convertEntityToStudent(StudentEntity studentEntity){
        if (studentEntity != null) {
            Student student = new Student();
            student.setId(studentEntity.getId());
            student.setName(studentEntity.getName());
            student.setEmail(studentEntity.getEmail());
            return student;
        } else {
            return null;
        }
    }

}
