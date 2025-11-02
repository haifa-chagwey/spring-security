package com.haifachagwey.springsecurity.student;

public class Student{

    private final Integer id;
    private final String name;
    private final String email;

    public Student(Integer id, String namel, String email) {
        this.id = id;
        this.name = namel;
        this.email = email;
    }

    public  Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

}
