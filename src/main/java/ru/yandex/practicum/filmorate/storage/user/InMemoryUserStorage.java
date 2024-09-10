package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idGenerator;

    public Collection<User> findAll() {
        return users.values();
    }

    public User getUser(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }

        throw new NotFoundException("No user with id = " + id);
    }

    public User create(User user) {
        user.setId(++idGenerator);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
        if (users.containsKey(newUser.getId())) {
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                newUser.setName(newUser.getLogin());
            }
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        log.warn("Пользователь с таким id не найдем");
        throw new NotFoundException("No user with id = " + newUser.getId());
    }

    public User addFriend(Long id, Long friendId) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("No user with id = " + id);
        }
        if (!users.containsKey(friendId)) {
            throw new NotFoundException("No user with id = " + friendId);
        }

        User user = users.get(id);
        User friend = users.get(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        return user;
    }

    public Collection<User> getFriends(long id) {

        if (users.containsKey(id)) {

            Set<Long> userFriend = users.get(id).getFriends();
            return users.values().stream()
                    .filter(user -> userFriend.contains(user.getId()))
                    .collect(Collectors.toList());
        }

        throw new NotFoundException("No user with id = " + id);
    }

    public User deleteFriend(long id, long friendId) {

        if (!users.containsKey(id)) {
            throw new NotFoundException("No user with id = " + id);
        }

        if (!users.containsKey(friendId)) {
            throw new NotFoundException("No user with id = " + friendId);

        }

        User user = users.get(id);
        user.getFriends().remove(friendId);
        User friend = users.get(friendId);
        friend.getFriends().remove(id);
        return user;
    }
}
