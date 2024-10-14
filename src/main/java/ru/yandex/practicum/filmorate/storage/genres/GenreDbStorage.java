package ru.yandex.practicum.filmorate.storage.genres;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
    private static final String GET_GENRES_BY_IDS = "SELECT id, name FROM genres WHERE id in (%S)";

    @Override
    public Optional<Genre> getGenreById(long id) {
        return jdbcTemplate.query(FIND_GENRE_BY_ID,
                (ResultSet rs) -> rs.next() ?
                        Optional.ofNullable(genreRowMapper.mapRow(rs, 1)) : Optional.empty(), id);
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query(FIND_ALL, genreRowMapper);
    }

    @Override
    public void insertFilmAndGenres(long filmId, Set<Long> genresId) {
        jdbcTemplate.batchUpdate(
                "MERGE   INTO film_genres (film_id, genre_id) KEY (film_id, genre_id) VALUES (?,?)",
                new BatchPreparedStatementSetter() {

                    private final List<Long> genresIdList = new ArrayList<>(genresId);

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genresIdList.get(i));
                    }

                    public int getBatchSize() {
                        return genresId.size();
                    }

                });

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

    @Override
    public List<Genre> getGenresByIds(Set<Long> genresIds) {
        String inSql = String.join(",", Collections.nCopies(genresIds.size(), "?"));
        return jdbcTemplate.query(String.format(GET_GENRES_BY_IDS, inSql), genreRowMapper, genresIds.toArray());
    }
}
