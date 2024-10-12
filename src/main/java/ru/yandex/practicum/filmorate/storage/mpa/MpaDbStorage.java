package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper;
    private static final String FIND_MPA_BY_ID = "SELECT * FROM mpa WHERE ID = ?";
    private static final String FIND_ALL = "SELECT * FROM mpa";

    @Override
    public Mpa getMpaById(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_MPA_BY_ID, mpaRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Введен неправильный рейтинг MPA");
        }
    }

    @Override
    public Collection<Mpa> getAll() {
        return jdbcTemplate.query(FIND_ALL, mpaRowMapper);
    }
}
