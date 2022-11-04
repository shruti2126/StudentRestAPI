package com.example.studentrestapi.service;

import com.example.studentrestapi.dao.StudentRepository;
import com.example.studentrestapi.entity.StudentEntity;
import com.example.studentrestapi.util.StudentEntityConverter;
import com.example.studentrestapi.vo.PagedResponse;
import com.example.studentrestapi.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("studentService")

public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepo;

    public List<Student> findAllStudents() {
        List<StudentEntity> Students = studentRepo.findAll();
        /*
         * ArrayList al=new ArrayList();
         *
         * for(int i=0;i<Students.size();i++) { Student Student=new Student(); StudentEntity
         * StudentEntity=(StudentEntity)Students.get(i); Student.setId(StudentEntity.getId());
         * Student.setName(StudentEntity.getName()); Student.setAge(StudentEntity.getAge());
         * Student.setSalary(StudentEntity.getSalary()); al.add(Student); } return al;
         */

        return Students.stream().map(e -> new Student(e.getId(), e.getName(), e.getEmail()))
                .collect(Collectors.toList());
    }

    public Student findById(long id) {
        StudentEntity StudentEntity = studentRepo.findById(id).orElse(null);
        return StudentEntityConverter.convertEntityToStudent(StudentEntity);
    }

    @Transactional
    public Student saveStudent(Student student) {
        StudentEntity StudentEntity = studentRepo.save(new StudentEntity(student.getId(), student.getName(), student.getEmail()));
        return StudentEntityConverter.convertEntityToStudent(StudentEntity);
    }

    public Student updateStudent(Student student) {
        StudentEntity StudentEntity = studentRepo.saveAndFlush(new StudentEntity(student.getId(), student.getName(), student.getEmail()));
        return StudentEntityConverter.convertEntityToStudent(StudentEntity);
    }

    public void deleteStudentById(long id) {
        studentRepo.deleteById(id);
    }

    public PagedResponse<Student> findPaginated(int page, int size, String orderBy) {

        Sort sort = null;
        if (orderBy != null) {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Page<StudentEntity> page1 = studentRepo.findAll(PageRequest.of(page, size, sort));
        List<StudentEntity> list = page1.getContent();
        PagedResponse<Student> result = new PagedResponse<>();
        result.setPage(page1.getNumber());
        result.setRows(page1.getSize());
        result.setTotalPage(page1.getTotalPages());
        result.setTotalElement(page1.getTotalElements());
        result.setOrder(page1.getSort().toString());
        result.setBody(list.stream().map(e -> new Student(e.getId(), e.getName(), e.getEmail()))
                .collect(Collectors.toList()));
        return result;
    }
}
