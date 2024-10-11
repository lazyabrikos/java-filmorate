package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
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
        User user = userStorage.getUser(newUser.getId());
        return userStorage.update(newUser);
    }

    public void addFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        friendsStorage.addFriend(id, friendId);
    }

    public Collection<User> getFriends(long id) {
        User user = userStorage.getUser(id);
        return friendsStorage.getFriendsIds(id).stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public void deleteFriend(long id, long friendId) {

        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        friendsStorage.removeFriend(id, friendId);
    }

    public Collection<User> getIntersectionFriends(long id, long otherId) {
        Collection<User> user1Friends = getFriends(id);
        Collection<User> user2Friends = getFriends(otherId);
        user1Friends.retainAll(user2Friends);
        return user1Friends;
    }
}
