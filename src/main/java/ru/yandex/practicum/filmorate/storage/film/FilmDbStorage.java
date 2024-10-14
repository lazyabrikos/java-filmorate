package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NegativeValueException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmRowMapper;
    private static final String FIND_BY_ID = "SELECT f.*, m.name AS mpa_name FROM films f " +
            "LEFT JOIN mpa m ON m.id = f.mpa_id WHERE f.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT f.*, m.id as mpa_id, m.name as mpa_name " +
            "FROM films f LEFT JOIN mpa m ON f.mpa_id = m.id";
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, release_date, duration, mpa_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, mpa_id = ? where id = ?";
    private static final String GET_POPULAR = "SELECT f.*, m.name AS mpa_name FROM films f JOIN ( " +
            "SELECT film_id, COUNT(user_id) AS rn " +
            "FROM likes l GROUP BY film_id " +
            "ORDER BY rn DESC LIMIT %d" +
            ") as subfilms ON f.ID = subfilms.film_id LEFT JOIN mpa m ON f.mpa_id = m.id ORDER BY subfilms.rn DESC";


    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, filmRowMapper);
    }

    @Override
    public Film create(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, film.getName());
            ps.setObject(2, film.getDescription());
            ps.setObject(3, film.getReleaseDate());
            ps.setObject(4, film.getDuration());
            ps.setObject(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        jdbcTemplate.update(UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getDuration(),
                newFilm.getMpa().getId(),
                newFilm.getId()
        );
        return newFilm;
    }

    @Override
    public Optional<Film> getFilm(long id) {
        return jdbcTemplate.query(FIND_BY_ID,
                (ResultSet rs) -> rs.next() ?
                        Optional.ofNullable(filmRowMapper.mapRow(rs, 1)) : Optional.empty(), id);
    }

    @Override
    public Collection<Film> getPopularFilms(long count) {
        if (count < 0) {
            throw new NegativeValueException("Validation error  ", "Count could not be negative");
        }
        return jdbcTemplate.query(String.format(GET_POPULAR, count), filmRowMapper);
    }
}
