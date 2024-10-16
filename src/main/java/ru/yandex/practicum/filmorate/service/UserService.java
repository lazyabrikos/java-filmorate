package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User getUser(long id) {
        return userStorage.getUser(id).orElseThrow(() -> new NotFoundException("Пользователь не найден с id = " + id));
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User newUser) {
        User user = getUser(newUser.getId());
        return userStorage.update(newUser);
    }

    public void addFriend(long id, long friendId) {
        User user = getUser(id);
        User friend = getUser(friendId);
        friendsStorage.addFriend(id, friendId);
    }

    public Collection<User> getFriends(long id) {
        User user = getUser(id);
        return friendsStorage.getFriendsIds(id).stream()
                .map(fiendId -> userStorage.getUser(fiendId)
                        .orElseThrow(() -> new NotFoundException("Пользователь не найден с id = " + fiendId)))
                .collect(Collectors.toList());
    }

    public void deleteFriend(long id, long friendId) {

        User user = getUser(id);
        User friend = getUser(friendId);
        friendsStorage.removeFriend(id, friendId);
    }

    public Collection<User> getIntersectionFriends(long id, long otherId) {
        Collection<User> user1Friends = getFriends(id);
        Collection<User> user2Friends = getFriends(otherId);
        user1Friends.retainAll(user2Friends);
        return user1Friends;
    }
}
