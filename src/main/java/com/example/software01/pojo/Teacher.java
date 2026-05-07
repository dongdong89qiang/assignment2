package com.example.software01.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<StudentTeacher> studentTeachers = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StudentTeacher> getStudentTeachers() {
        return studentTeachers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentTeachers(List<StudentTeacher> studentTeachers) {
        this.studentTeachers = studentTeachers;
    }
}