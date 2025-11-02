package com.haifachagwey.springsecurity.student;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {


//  hasRole('_ROLE') hasAnyRole('_ROLE1','_ROLE2')
//  hasAuthority('_AUTHORITY') hasAnyAuthority('_AUTHORITY1','_AUTHORITY2')

    private final List<Student> students = List.of(
            new Student(1, "Alice Johnson", "alice@example.com"),
            new Student(2, "Bob Smith", "bob@example.com"),
            new Student(3, "Charlie Brown", "charlie@example.com")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        System.out.println(String.format("%s %s", id, student));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer id) {
        System.out.println(id);
    }
}
