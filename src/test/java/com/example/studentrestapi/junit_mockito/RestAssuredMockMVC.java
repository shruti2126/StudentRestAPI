package com.example.studentrestapi.junit_mockito;

import com.example.studentrestapi.controller.StudentRestController;
import com.example.studentrestapi.service.StudentService;
import com.example.studentrestapi.vo.Student;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


public class RestAssuredMockMVC {

    @Mock
    StudentService studentService;

    @Before
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new StudentRestController(studentService));

    }

    @Test
    public void testCreateStudent() {
        Student testStudent = new Student(null, "test name", "test@email.com");
        Student savedStudent = new Student(1L, "test name", "test@email.com");
        when(studentService.saveStudent(any())).thenReturn(savedStudent);
        given().accept("application/json").contentType("application/json").body(testStudent)
                .post("/api/student")
                .peek()
                .then().assertThat()
                .statusCode(201)
                .body("data.id", Matchers.is(1));
    }

    @Test
    public void testCreateStudentWithException() {
        Student testStudent = new Student(1L, "test name", "test@email");
        Student savedStudent = new Student(1L, "test name", "test@email");
        when(studentService.saveStudent(any())).thenThrow(new RuntimeException("dummy error"));
        given().accept("application/json").contentType("application/json").body(testStudent)
                .post("/api/student")
                .peek()
                .then().assertThat()
                .statusCode(500)
                .body("message", Matchers.is("dummy error"));
    }

    @Test
    public void testUpdateStudent() {

    }
}
