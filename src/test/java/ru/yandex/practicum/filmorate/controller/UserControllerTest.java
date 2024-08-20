package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addNewUser() {
        User user = new User(
                "vovag2003@gmail.com",
                "vovag",
                "vovag",
                LocalDate.of(2003, 1, 4)
        );
        ResponseEntity<User> response = restTemplate.postForEntity( "/users" , user, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void addUserWithWrongLogin() {
        User user = new User(
                "vovag2003@gmail.com",
                "vov ag",
                "vovag",
                LocalDate.of(2003, 1, 4)
        );
        ResponseEntity<User> response = restTemplate.postForEntity( "/users" , user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addUserWithBirthdayDateInFuture() {
        User user = new User(
                "vovag2003@gmail.com",
                "vovag",
                "vovag",
                LocalDate.of(2025, 1, 4)
        );
        ResponseEntity<User> response = restTemplate.postForEntity( "/users" , user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addUserWithInCorrectEmail() {
        User user = new User(
                "aaa",
                "vovag",
                "vovag",
                LocalDate.of(2003, 1, 4)
        );
        ResponseEntity<User> response = restTemplate.postForEntity( "/users" , user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
