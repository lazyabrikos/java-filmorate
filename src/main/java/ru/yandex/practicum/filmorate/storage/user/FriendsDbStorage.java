package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_FRIEND = "INSERT INTO friendship (user_id, friend_id) VALUES(?, ?)";
    private static final String DELETE_FRIEND = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
    private static final String GET_FRIEND_IDS = "SELECT friend_id FROM friendship WHERE user_id = ?";
    @Override
    public void addFriend(long id, long friendId) {
        jdbcTemplate.update(INSERT_FRIEND, id, friendId);
    }

    @Override
    public void removeFriend(long id, long friendId) {
        jdbcTemplate.update(DELETE_FRIEND, id, friendId);
    }

    @Override
    public List<Long> getFriendsIds(long userId) {
        return jdbcTemplate.queryForList(GET_FRIEND_IDS, Long.class, userId);
    }

}
