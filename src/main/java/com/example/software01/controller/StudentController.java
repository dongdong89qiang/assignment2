package com.example.software01.controller;

import com.example.software01.pojo.Student;
import com.example.software01.pojo.Teacher;
import com.example.software01.repositories.StudentRepository;
import com.example.software01.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;
    private StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }
       @GetMapping("/{id}")
     public Optional<Student> getStudentById(@PathVariable Long id){
           return studentRepository.findById(id);

    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
          @GetMapping("/{id}/teachers")
    public List<Teacher> getTeacherByStudentId(@PathVariable Long id){
       return studentService.getTeacherByStudentId(id);
    }
}
