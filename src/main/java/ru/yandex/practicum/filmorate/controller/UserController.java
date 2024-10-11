package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        logger.info("Пришел запрос GET /users");
        Collection<User> response = userService.findAll();
        logger.info("Отправлен ответ GET /users с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        logger.info("Пришел запрос GET /users/{}", id);
        User response = userService.getUser(id);
        logger.info("Отправлен ответ GET /users/{} с телом {}", id, response);
        return response;
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        logger.info("Пришел запрос GET /users/{}/friends", id);
        Collection<User> response = userService.getFriends(id);
        logger.info("Отправлен ответ GET /users/{}/friends с телом {}", id, response);
        return response;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        logger.info("Пришел запрос POST /users с телом: {}", user);
        User response = userService.create(user);
        logger.info("Отправлен ответ POST /users с телом: {}", response);
        return response;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        logger.info("Пришел запрос PUT /users с телом: {}", newUser);
        User response = userService.update(newUser);
        logger.info("Отправлен ответ PUT /users с телом: {}", response);
        return response;
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        logger.info("Пришел запрос PUT /users/{}/friends/{}", id, friendId);
        userService.addFriend(id, friendId);
        logger.info("У пользователя с id = {} добавлен друг с id = {}", id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        logger.info("Пришел запрос DELETE  /users/{}/friends/{}", id, friendId);
        userService.deleteFriend(id, friendId);
        logger.info("У пользователя с id = {} удален друг с id = {}", id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getIntersectionFriends(@PathVariable long id, @PathVariable long otherId) {
        logger.info("Пришел запрос GET /users/{}/friends/common/{}", id, otherId);
        Collection<User> response = userService.getIntersectionFriends(id, otherId);
        logger.info("Отправлен ответ GET /users/{}/friends/common/{} с телом {}", id, otherId, response);
        return response;
    }

}
