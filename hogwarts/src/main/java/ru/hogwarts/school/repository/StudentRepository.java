package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(int age);
    List<Student> findAllByAgeBetween(int minAge, int maxAge);
    List<Student> findAllByFaculty_Id(long facultyId);

    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();
    @Query("SELECT AVG(s.age) FROM Student s")
    Double findAverageAge();
    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findTop5Students();
}
