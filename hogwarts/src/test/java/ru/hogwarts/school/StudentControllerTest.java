package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Student testStudent;

    @BeforeEach
    public void setUp() {
        testStudent = new Student();
        testStudent.setName("John Doe");
        testStudent.setAge(20);
        // Установите остальные необходимые поля
    }

    @Test
    public void createStudent() {
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", testStudent, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(testStudent.getName());
    }

    @Test
    public void updateStudent() {
        // Предположим, что студент с ID 1 существует
        testStudent.setName("Jane Doe");
        restTemplate.put("/student/1", testStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/1", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void getStudent() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/1", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void removeStudent() {
        // Предположим, что студент с ID 1 существует
        ResponseEntity<Student> response = restTemplate.exchange("/student/1", HttpMethod.DELETE, null, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Student> getResponse = restTemplate.getForEntity("/student/1", Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void filterByAge() {
        ResponseEntity<List> response = restTemplate.getForEntity("/student?age=20", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void filterByRangeAge() {
        ResponseEntity<List> response = restTemplate.getForEntity("/student?minAge=18&maxAge=22", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void findStudentsFaculty() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/1/faculty", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getAvatarFromDb() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity("/student/1/avatar-from-db", byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getAvatarFromFs() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity("/student/1/avatar-from-fs", byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}