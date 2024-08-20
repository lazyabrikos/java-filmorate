package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        logger.trace("Got request: get all films");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        logger.trace("Got request: add new user");
        user.setId(getNextId());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        logger.info("New user successfully added with id = {}", user.getId());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (users.containsKey(newUser.getId())) {
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                newUser.setName(newUser.getLogin());
            }
            users.put(newUser.getId(), newUser);
            logger.info("User with id = {} successfully updates", newUser.getId());
            return newUser;
        }
        logger.warn("No film found with id");
        throw new NotFoundException("No user with id = " + newUser.getId());
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
