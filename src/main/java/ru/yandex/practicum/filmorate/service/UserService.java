package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        return user;
    }

    public Collection<User> getFriends(long id) {
        User user = userStorage.getUser(id);
        Set<Long> userFriends = user.getFriends();
        return userFriends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public User deleteFriend(long id, long friendId) {

        User user = userStorage.getUser(id);
        user.getFriends().remove(friendId);
        User friend = userStorage.getUser(friendId);
        friend.getFriends().remove(id);
        return user;
    }

    public Collection<User> getIntersectionFriends(long id, long otherId) {
        Collection<User> user1Friends = getFriends(id);
        Collection<User> user2Friends = getFriends(otherId);
        user1Friends.retainAll(user2Friends);
        return user1Friends;
    }
}
