package com.haifachagwey.springsecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
public class StudentController {

    private final List<Student> students = List.of(
            new Student(1, "Alice Johnson", "alice@example.com"),
            new Student(2, "Bob Smith", "bob@example.com"),
            new Student(3, "Charlie Brown", "charlie@example.com")
    );
    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return students.stream()
                .filter(student -> id.equals(student.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist"));
    }

}
