package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        log.info("Пришел запрос GET /users");
        Collection<User> response = userService.findAll();
        log.info("Отправлен ответ GET /users с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Пришел запрос GET /users/{}", id);
        User response = userService.getUser(id);
        log.info("Отправлен ответ GET /users/{} с телом {}", id, response);
        return response;
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        log.info("Пришел запрос GET /users/{}/friends", id);
        Collection<User> response = userService.getFriends(id);
        log.info("Отправлен ответ GET /users/{}/friends с телом {}", id, response);
        return response;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Пришел запрос POST /users с телом: {}", user);
        User response = userService.create(user);
        log.info("Отправлен ответ POST /users с телом: {}", response);
        return response;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Пришел запрос PUT /users с телом: {}", newUser);
        User response = userService.update(newUser);
        log.info("Отправлен ответ PUT /users с телом: {}", response);
        return response;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пришел запрос PUT /users/{}/friends/{}", id, friendId);
        userService.addFriend(id, friendId);
        log.info("У пользователя с id = {} добавлен друг с id = {}", id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пришел запрос DELETE  /users/{}/friends/{}", id, friendId);
        userService.deleteFriend(id, friendId);
        log.info("У пользователя с id = {} удален друг с id = {}", id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getIntersectionFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Пришел запрос GET /users/{}/friends/common/{}", id, otherId);
        Collection<User> response = userService.getIntersectionFriends(id, otherId);
        log.info("Отправлен ответ GET /users/{}/friends/common/{} с телом {}", id, otherId, response);
        return response;
    }

}
