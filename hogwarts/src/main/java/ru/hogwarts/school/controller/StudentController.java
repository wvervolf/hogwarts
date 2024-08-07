package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Студенты", description = "Эндпоинты для работы со студентами")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService,
                             AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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
    public List<Student> filterByAge(@RequestParam int age) {
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

    @GetMapping("/{id}/avatar-from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable long id) {
        return buildResponseEntity(avatarService.getAvatarFromDb(id));
    }

    @GetMapping("/{id}/avatar-from-fs")
    public ResponseEntity<byte[]> getAvatarFromFs(@PathVariable long id) {
        return buildResponseEntity(avatarService.getAvatarFromFs(id));
    }

    private ResponseEntity<byte[]> buildResponseEntity(Pair<byte[], String> pair) {
        byte[] data = pair.getFirst();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(data);
    }
}
