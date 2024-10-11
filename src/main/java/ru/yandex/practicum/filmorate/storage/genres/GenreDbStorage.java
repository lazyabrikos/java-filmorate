package ru.yandex.practicum.filmorate.storage.genres;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreRowMapper genreRowMapper;
    private static final String FIND_ALL = "SELECT * FROM genres";
    private static final String FIND_GENRE_BY_ID = "SELECT * FROM genres WHERE id = ?";
    private static final String INSERT_GENRES_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String DELETE_ALL_RELATIONS_FOR_ONE_FILM = "DELETE FROM film_genres " +
            "WHERE film_id = ?";
    private static final String GET_GENRES_FOR_ONE_FILM = "SELECT g.id, g.NAME FROM film_genres fg " +
            "LEFT JOIN GENRES g ON fg.GENRE_ID = g.id WHERE fg.FILM_ID = ?";

    @Override
    public Genre getGenreById(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_GENRE_BY_ID, genreRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр не найден с id = " + id);
        }
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query(FIND_ALL, genreRowMapper);
    }

    @Override
    public void insertFilmAndGenres(long filmId, Set<Long> genresId) {
        for (Long id : genresId) {
            jdbcTemplate.update(INSERT_GENRES_QUERY, filmId, id);
        }
    }

    @Override
    public void update(long filmId, Set<Long> genresId) {
        jdbcTemplate.update(DELETE_ALL_RELATIONS_FOR_ONE_FILM, filmId);
        insertFilmAndGenres(filmId, genresId);
    }

    @Override
    public Collection<Genre> getGenresForFilm(Long filmId) {
        return jdbcTemplate.query(GET_GENRES_FOR_ONE_FILM, genreRowMapper, filmId);
    }
}
