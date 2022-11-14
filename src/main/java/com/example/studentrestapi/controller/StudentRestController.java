package com.example.studentrestapi.controller;

import com.example.studentrestapi.exception.StudentException;
import com.example.studentrestapi.exception.StudentNameIsNullException;
import com.example.studentrestapi.exception.StudentNotFoundException;
import com.example.studentrestapi.service.StudentService;
import com.example.studentrestapi.util.Constants;
import com.example.studentrestapi.vo.ErrorResponse;
import com.example.studentrestapi.vo.PagedResponse;
import com.example.studentrestapi.vo.ResponseMessage;
import com.example.studentrestapi.vo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api")
@Api(value = "Student", description = "REST API for Students", tags={"Student"})
public class StudentRestController {

    StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }
    /**
     * GET - retrives single student
     *
     **/
    @ApiOperation(value = "gets a single student")
    @RequestMapping(value = "/student/{uid}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudent(@PathVariable("uid") long id) throws StudentException {
        Student student = studentService.findById(id);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    //http://localhost:8009/swagger-ui.html#/

    /**
     * POST - create a student
     */
    @ApiOperation(value = "create a student")
    @RequestMapping(value = "/student", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponseMessage> createStudent(@Validated @RequestBody Student student, UriComponentsBuilder ucBuilder) {
        Student savedStudent = studentService.saveStudent(student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Student has been created successfully",savedStudent), headers, HttpStatus.CREATED);
    }

    /**
     * PUT - update a student
     *
     **/
    @ApiOperation(value = "update a student")
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student){
        Student currentStudent = studentService.findById(id);

        if (currentStudent == null) {
            throw new StudentNotFoundException();
        }

        currentStudent.setName(student.getName());
        currentStudent.setEmail(student.getEmail());

        studentService.updateStudent(currentStudent);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
    }

    /**
     * delete a student
     *
     * @throws StudentException
     **/
    @ApiOperation(value = "delete a student")
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable("id") long id) {

        Student student = studentService.findById(id);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        studentService.deleteStudentById(id);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("STUDENT_DELETED",student), HttpStatus.OK);
    }

    /**
     * Get all students in the database
     */
    @ApiOperation(value = "get students accordingly")
    @RequestMapping(value = "/students",  method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<Student>> getAllStudents(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(required = false, defaultValue = "5") Integer rows,
                                                                 @RequestParam(required = false, defaultValue = "name") String orderBy) {

        PagedResponse<Student> students = studentService.findPaginated(pageNo, rows, orderBy);
        if (students.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return new ResponseEntity<PagedResponse<Student>>(students, HttpStatus.OK);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerStudentNotFound(Exception ex) {
//        logger.error("Cannot find student");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
