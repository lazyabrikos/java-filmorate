package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FilmControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addNewFilm() {
        Film film = new Film(
                "Star Wars",
                "Long long ago....",
                LocalDate.of(1977, 5, 25),
                180
        );
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void addFilmWithBlankName() {
        Film film = new Film(
                "",
                "Long long ago..",
                LocalDate.of(1977, 5, 25),
                180
        );
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addFilmWithReleaseDateEarlierThen28December1895() {
        Film film = new Film(
                "Tmp",
                "Tmp",
                LocalDate.of(1800, 5, 25),
                100
        );
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addFilmWithNegativeDuration() {
        Film film = new Film(
                "Tmp",
                "Tmp",
                LocalDate.of(1977, 5, 25),
                -100
        );
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
