package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User getUser(long id) {
        return userStorage.getUser(id);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User newUser) {
        return userStorage.update(newUser);
    }

    public User addFriend(long id, long friendId) {
        return userStorage.addFriend(id, friendId);
    }

    public Collection<User> getFriends(long id) {
        return userStorage.getFriends(id);
    }

    public User deleteFriend(long id, long friendId) {
        return userStorage.deleteFriend(id, friendId);
    }
}
