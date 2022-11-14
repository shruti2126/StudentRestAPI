package com.example.studentrestapi.get_delete_bean_injection;

import com.example.studentrestapi.controller.StudentRestController;
import com.example.studentrestapi.dao.StudentRepository;
import com.example.studentrestapi.entity.StudentEntity;
import com.example.studentrestapi.exception.StudentException;
import com.example.studentrestapi.exception.StudentNotFoundException;
import com.example.studentrestapi.vo.PagedResponse;
import com.example.studentrestapi.vo.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class StudentRestApiApplicationTests {

    @Autowired
    StudentRestController controller;

    @Autowired
    StudentRepository repo;


    @BeforeAll
    public static void beforeAll(){

    }

    @Test
    void testGetAllStudents() {
        try {
            ResponseEntity<PagedResponse<Student>> res = controller.getAllStudents(0, 3, "id");
            assertEquals(res.getStatusCode(), HttpStatus.OK);
        } catch(Exception e) {
           if(createNewStudent(e)){
               testGetAllStudents();
           }
        }

    }

    @Test
    void testDeleteStudent() {

    }

    private boolean createNewStudent(Exception e){
        if(e.getMessage()=="STUDENT_NOT_FOUND"){
            //database empty, create new student
            Student newStudent = new Student("Shruti", "Shruti@gmail.com");
            try {
                if(controller.createStudent(newStudent, UriComponentsBuilder.newInstance()).getStatusCode().toString().equals("CREATED")) {
                    //student created successfully, return true
                    return true;
                }

            }catch(Exception ex){
                System.out.println(ex.getMessage());

            }
        }
        return false;
    }
}
