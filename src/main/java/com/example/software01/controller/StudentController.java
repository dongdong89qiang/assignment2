package com.example.software01.controller;

import com.example.software01.dto.StudentDto;
import com.example.software01.dto.TeacherDto;
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
     public Optional<StudentDto> getStudent(@PathVariable Long id){
        return studentRepository.findById(id).map((Student student) -> new StudentDto(student.getId(),student.getName()));

     }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
          @GetMapping("/{id}/teachers")
    public List<TeacherDto> getTeacherByStudentId(@PathVariable Long id){
       return studentService.getTeacherByStudentId(id);
    }
}
