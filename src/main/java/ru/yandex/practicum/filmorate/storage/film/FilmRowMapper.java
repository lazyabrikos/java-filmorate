package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDuration(rs.getLong("duration"));
        film.setDescription(rs.getString("description"))    ;
        film.setMpa(new Mpa(rs.getLong("mpa_id"), rs.getString("mpa_name")));
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        film.setReleaseDate(releaseDate);
        return film;
    }
}
