package com.example.studentrestapi.service;

import com.example.studentrestapi.vo.PagedResponse;
import com.example.studentrestapi.vo.Student;

import java.util.List;


public interface StudentService {

    Student findById(long id);

    Student saveStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudentById(long id);

    List<Student> findAllStudents();

    PagedResponse<Student> findPaginated(int page, int size, String orderBy);


}
