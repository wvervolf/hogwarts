package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Faculty testFaculty;

    @BeforeEach
    public void setUp() {
        testFaculty = new Faculty();
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Blue");
    }

    @Test
    public void createFaculty() {
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(testFaculty.getName());
    }

    @Test
    public void updateFaculty() {
        // Создаем факультет для обновления
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        long facultyId = createdResponse.getBody().getId();

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("Updated Faculty");
        updatedFaculty.setColor("Red");

        HttpEntity<Faculty> requestEntity = new HttpEntity<>(updatedFaculty);
        restTemplate.exchange("/faculty/" + facultyId, HttpMethod.PUT, requestEntity, Void.class);

        // Проверяем обновленный факультет
        ResponseEntity<Faculty> updatedResponse = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(updatedResponse.getBody().getName()).isEqualTo(updatedFaculty.getName());
    }

    @Test
    public void getFaculty() {
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        long facultyId = createdResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(facultyId);
    }

    @Test
    public void removeFaculty() {
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        long facultyId = createdResponse.getBody().getId();

        // Удаляем факультет
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/" + facultyId, HttpMethod.DELETE, null, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Проверяем, что факультет удален
        ResponseEntity<Faculty> getResponse = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void filterByColor() {
        restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);

        ResponseEntity<List> response = restTemplate.exchange("/faculty?color=Blue", HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void filterByColorOrName() {
        restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);

        ResponseEntity<List> response = restTemplate.exchange("/faculty?colorOrName=Test Faculty", HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void findStudentsByFacultyId() {
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        long facultyId = createdResponse.getBody().getId();

        // Предполагается, что у факультета есть студенты. Вам нужно будет добавить логику для создания студентов и проверки.
        ResponseEntity<List<Student>> response = restTemplate.exchange("/faculty/" + facultyId + "/students", HttpMethod.GET, null, (Class<List<Student>>) (Object) List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Проверка на наличие студентов или других условий в зависимости от вашей логики.
    }
}