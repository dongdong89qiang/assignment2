package com.example.software01.repositories;

import com.example.software01.pojo.StudentTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTeacherRepository extends JpaRepository<StudentTeacher, Long> {

    List<StudentTeacher> findByStudent_Id(Long studentId);

    List<StudentTeacher> findByTeacher_Id(Long teacherId);
}