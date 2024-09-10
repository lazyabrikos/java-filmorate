package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        logger.info("Пришел Get запрос всех пользователей");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        logger.info("Пришел Post запрос /users с телом: {}", user);
        logger.info("Отправлен ответ Post /users с телом: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        logger.info("Пришел запрос Put /users с телом: {}", newUser);
        logger.info("Отправлен ответ Put /users с телом: {}", newUser);
        return userService.update(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(
            @PathVariable long id,
            @PathVariable long friendId
    ) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(
            @PathVariable long id,
            @PathVariable long friendId
    ) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getIntersectionFriends(
            @PathVariable long id,
            @PathVariable long otherId
    ) {
        Collection<User> user1Friends = userService.getFriends(id);
        Collection<User> user2Friends = userService.getFriends(otherId);
        user1Friends.retainAll(user2Friends);
        return user1Friends;
    }

}
