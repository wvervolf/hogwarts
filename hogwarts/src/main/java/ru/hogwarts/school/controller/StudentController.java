package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Студенты", description = "Эндпоинты для работы со студентами")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Эндпоинт для создания студента")
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody Student student) {
        studentService.update(id, student);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id) {
        return studentService.get(id);
    }

    @DeleteMapping("/{id}")
    public Student remove(@PathVariable long id) {
        return studentService.remove(id);
    }

    @GetMapping(params = "age")
    public List<Student> filterByAge(@RequestParam int age){
        return studentService.filterByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public List<Student> filterByRangeAge(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.filterByRangeAge(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public Faculty findStudentsFaculty(@PathVariable long id) {
        return studentService.findStudentsFaculty(id);
    }
}
