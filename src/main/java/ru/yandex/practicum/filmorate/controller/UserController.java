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
    private long idGenerator = 0;

    @GetMapping
    public Collection<User> findAll() {
        logger.info("Пришел Get запрос всех пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        logger.info("Пришел Post запрос /users с телом: {}", user);
        user.setId(++idGenerator);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        logger.info("Отправлен ответ Post /users с телом: {}", user);
        logger.info("Новый пользователь успешно добавлен с id = {}", user.getId());

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        logger.info("Пришел запрос Put /users с телом: {}", newUser);
        if (users.containsKey(newUser.getId())) {
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                newUser.setName(newUser.getLogin());
            }
            users.put(newUser.getId(), newUser);
            logger.info("Отправлен ответ Put /users с телом: {}", newUser);
            logger.info("Пользователь с id = {} успешно обновлен", newUser.getId());
            return newUser;
        }
        logger.warn("Пользователь с таким id не найдем");
        throw new NotFoundException("No user with id = " + newUser.getId());
    }
}
