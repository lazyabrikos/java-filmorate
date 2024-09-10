package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> findAll();

    User create(User user);

    User update(User user);

    User addFriend(Long id, Long friendId);

    User getUser(long id);

    Collection<User> getFriends(long id);

    User deleteFriend(long id, long friendId);
}
