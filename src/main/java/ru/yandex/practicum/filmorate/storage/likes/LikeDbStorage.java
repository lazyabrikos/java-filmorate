package ru.yandex.practicum.filmorate.storage.likes;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_LIKE_QUERY = "INSERT INTO likes (user_id, film_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";

    @Override
    public void addLike(Long userId, Long filmId) {
        jdbcTemplate.update(INSERT_LIKE_QUERY, userId, filmId);
    }

    @Override
    public void deleteLike(Long userId, Long filmId) {
        jdbcTemplate.update(DELETE_LIKE_QUERY, userId, filmId);
    }

}
