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

    @Override
    public void addFriend(long id, long friendId) {
        final String checkRelationAccept = "SELECT relation_accept FROM friendship " +
                "WHERE user_id = ? AND friend_id = ?";
        String relationAccept;
        try {
            relationAccept = jdbcTemplate.queryForObject(checkRelationAccept, String.class, friendId, id);
        } catch (EmptyResultDataAccessException e) {
            relationAccept = null;
        }
        String query;
        if (relationAccept == null) {
            query = "INSERT INTO friendship (relation_accept, user_id, friend_id) " +
                    "VALUES (?, ?, ?)";
            relationAccept = "Not accepted";
        } else {
            query = "UPDATE friendship " +
                    "SET relation_accept = ? " +
                    "WHERE friend_id = ? AND user_id = ?";
            relationAccept = "Accepted";
        }
        jdbcTemplate.update(query, relationAccept, id, friendId);
    }

    @Override
    public void removeFriend(long id, long friendId) {
        final String checkRelationAccept = "SELECT relation_accept FROM friendship " +
                "WHERE user_id = ? AND friend_id = ?";
        String relationAccept;
        try {
            relationAccept = jdbcTemplate.queryForObject(checkRelationAccept, String.class, id, friendId);
        } catch (EmptyResultDataAccessException e) {
            relationAccept = null;
        }

        String query;
        if (relationAccept == null) {
            query = "UPDATE friendship SET relation_accept = \'Not accepted\' WHERE user_id = ? AND friend_id = ?";
            System.out.println(query);
        } else if (relationAccept.equals("Not accepted")) {
            query = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        } else {
            final String insert_query = "INSERT INTO friendship (relation_accept, user_id, friend_id) VALUES (?, ?, ?)";
            jdbcTemplate.update(insert_query, "Not accepted", friendId, id);
            query = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        }
        jdbcTemplate.update(query, id, friendId);
    }

    @Override
    public List<Long> getFriendsIds(long userId) {
        final String query = "SELECT friend_id FROM friendship WHERE user_id = ? " +
                "UNION SELECT user_id FROM friendship WHERE friend_id = ? AND relation_accept = ?";
        return jdbcTemplate.queryForList(query, Long.class, userId, userId, "Accepted");
    }

}
