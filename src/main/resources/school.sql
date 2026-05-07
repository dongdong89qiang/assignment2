USE moviedb;

CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE teachers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE student_teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    student_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,

    CONSTRAINT uq_student_teacher
        UNIQUE (student_id, teacher_id),

    CONSTRAINT fk_student
        FOREIGN KEY (student_id)
        REFERENCES students(id),

    CONSTRAINT fk_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teachers(id)
);