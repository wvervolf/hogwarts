package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudenNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long idGenerator = 1;

    public Student create(Student student) {
        student.setId(idGenerator++);
        students.put(student.getId(), student);
        return student;
    }

    public void update(long id, Student student) {
        if (!students.containsKey(id)) {
            throw new StudenNotFoundException(id);
        }
        student.setId(id);
        students.replace(id, student);
        /*Student oldStudent = students.get(id);
        oldStudent.setName(student.getName());
        oldStudent.setColor(student.getColor());*/
    }

    public Student get(long id) {
        if (!students.containsKey(id)) {
            throw new StudenNotFoundException(id);
        }
        return students.get(id);
    }

    public Student remove(long id) {
        if (!students.containsKey(id)) {
            throw new StudenNotFoundException(id);
        }
        return students.remove(id);
    }

    public List<Student> filterByColor(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
