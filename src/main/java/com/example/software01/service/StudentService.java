package com.example.software01.service;

import com.example.software01.pojo.StudentTeacher;
import com.example.software01.pojo.Teacher;
import com.example.software01.repositories.StudentTeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {
    private StudentTeacherRepository studentTeacherRepository;

    public StudentService(StudentTeacherRepository studentTeacherRepository){
        this.studentTeacherRepository =studentTeacherRepository;
    }
   public List<Teacher> getTeacherByStudentId(Long sudentId){
       return studentTeacherRepository.findByStudent_Id(sudentId).stream().map(StudentTeacher :: getTeacher).toList();

   }

}