package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudenNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public void update(long id, Student student) {
        Student oldStudent = studentRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());
        studentRepository.save(oldStudent);
    }

    public Student get(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Student remove(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        studentRepository.delete(student);
        return student;
    }

    public List<Student> filterByAge(int age) {
        return studentRepository.findAllByAge(age);
    }
}
