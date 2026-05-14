package com.example.software01.repositories;

import com.example.software01.pojo.StudentTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//StudentTeacherRepository 因为extends JpaRepository
//SpringBoot启动时，Spring Data JPA 会自动扫描“继承 JpaRepository 的 interface”：自动创建了 Bean
//Repository 本质上是动态代理，动态生成了代理对象
//不用写@Repository
public interface StudentTeacherRepository extends JpaRepository<StudentTeacher, Long> {

    List<StudentTeacher> findByStudent_Id(Long studentId);

    List<StudentTeacher> findByTeacher_Id(Long teacherId);
}