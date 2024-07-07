package ru.hogwarts.school.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Student {

    @Schema(name = "id", description = "id студента")
    private Long id;

    @Schema(name = "name", description = "Имя студента")
    private String name;

    @Schema(name = "age", description = "Возраст студента")
    private int age;

    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
