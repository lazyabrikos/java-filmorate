package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper;
    private static final String FIND_MPA_BY_ID = "SELECT * FROM mpa WHERE ID = ?";
    private static final String FIND_ALL = "SELECT * FROM mpa";

    @Override
    public Optional<Mpa> getMpaById(long id) {
        return jdbcTemplate.query(FIND_MPA_BY_ID,
                (ResultSet rs) -> rs.next() ?
                        Optional.ofNullable(mpaRowMapper.mapRow(rs, 1)) : Optional.empty(), id);
    }

    @Override
    public Collection<Mpa> getAll() {
        return jdbcTemplate.query(FIND_ALL, mpaRowMapper);
    }
}
