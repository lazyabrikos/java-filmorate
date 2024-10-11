package ru.yandex.practicum.filmorate.storage.user;

import java.util.List;

public interface FriendsStorage {
    void addFriend(long id, long friendId);

    void removeFriend(long id, long friendId);

    List<Long> getFriendsIds(long id);
}
