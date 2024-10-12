package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmServiceTest {

    private final FilmService filmService;
    private final Film film = new Film("Test",
            "description",
            LocalDate.now(),
            100L,
            new Mpa(1L, null),
            Set.of(new Genre(1L, null))
    );
    private Film newFilm;

    @BeforeEach
    public void addFilm() {
        newFilm = filmService.create(film);
    }


    @Test
    public void createFilmTest() {
        assertThat(newFilm).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(newFilm).hasFieldOrPropertyWithValue("name", film.getName());
        assertThat(newFilm).hasFieldOrPropertyWithValue("description", film.getDescription());
        assertThat(newFilm).hasFieldOrPropertyWithValue("releaseDate", film.getReleaseDate());
        assertThat(newFilm).hasFieldOrPropertyWithValue("duration", film.getDuration());
        assertEquals(newFilm.getGenres().size(), 1);
        assertEquals(new ArrayList<>(newFilm.getGenres()).getFirst().getName(), "Комедия");
        assertEquals(newFilm.getMpa().getId(), film.getMpa().getId());
        assertEquals(newFilm.getMpa().getName(), "G");
    }

    @Test
    public void getFilmByIdTest() {
        Film filmDb = filmService.getFilm(film.getId());

        assertThat(filmDb).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(filmDb).hasFieldOrPropertyWithValue("name", film.getName());
        assertThat(filmDb).hasFieldOrPropertyWithValue("description", film.getDescription());
        assertThat(filmDb).hasFieldOrPropertyWithValue("releaseDate", film.getReleaseDate());
        assertThat(filmDb).hasFieldOrPropertyWithValue("duration", film.getDuration());
        assertEquals(filmDb.getGenres().size(), 1);
        assertEquals(new ArrayList<>(filmDb.getGenres()).getFirst().getName(), "Комедия");
        assertEquals(filmDb.getMpa().getId(), film.getMpa().getId());
        assertEquals(filmDb.getMpa().getName(), "G");
    }

    @Test
    public void getAllFilmsTest() {
        Collection<Film> films = filmService.findAll();

        assertEquals(films.size(), 1);
    }

    @Test
    public void updateFilmTest() {
        newFilm.setName("Updated name");
        Film updatedFilm = filmService.update(newFilm);

        assertThat(updatedFilm).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("name", newFilm.getName());
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("description", newFilm.getDescription());
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("releaseDate", newFilm.getReleaseDate());
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("duration", newFilm.getDuration());
        assertEquals(updatedFilm.getGenres().size(), 1);
        assertEquals(new ArrayList<>(updatedFilm.getGenres()).getFirst().getName(), "Комедия");
        assertEquals(updatedFilm.getMpa().getId(), newFilm.getMpa().getId());
        assertEquals(updatedFilm.getMpa().getName(), "G");

    }
}
