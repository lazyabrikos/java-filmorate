package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idGenerator;

    public Collection<User> findAll() {
        return users.values();
    }

    public User getUser(long id) {
        checkUserAvailability(id);
        return users.get(id);
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
        checkUserAvailability(newUser.getId());
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public void checkUserAvailability(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("No user with id = " + id);
        }
    }
}
