package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    private final UserService userService;
    private final User user = new User("tmp@mail.com", "tmpLogin", "Vova", LocalDate.now());
    private User newUser;

    @BeforeEach
    public void addUser() {
        newUser = userService.create(user);
    }

    @Test
    public void createUserTest() {
        assertThat(newUser).hasFieldOrPropertyWithValue("id", newUser.getId());
        assertThat(newUser).hasFieldOrPropertyWithValue("name", user.getName());
        assertThat(newUser).hasFieldOrPropertyWithValue("login", user.getLogin());
        assertThat(newUser).hasFieldOrPropertyWithValue("birthday", user.getBirthday());
        assertThat(newUser).hasFieldOrPropertyWithValue("email", user.getEmail());
    }

    @Test
    public void getUserByIdTest() {
        User userDb = userService.getUser(newUser.getId());

        assertThat(newUser).hasFieldOrPropertyWithValue("id", newUser.getId());
        assertThat(newUser).hasFieldOrPropertyWithValue("name", user.getName());
        assertThat(newUser).hasFieldOrPropertyWithValue("login", user.getLogin());
        assertThat(newUser).hasFieldOrPropertyWithValue("birthday", user.getBirthday());
        assertThat(newUser).hasFieldOrPropertyWithValue("email", user.getEmail());
    }

    @Test
    public void getAllUsersTest() {
        Collection<User> users = userService.findAll();

        assertEquals(users.size(), 1);
    }

    @Test
    public void updateUserTest() {
        newUser.setName("Ivan");
        User updatedUser = userService.update(newUser);

        assertThat(updatedUser).hasFieldOrPropertyWithValue("id", newUser.getId());
        assertThat(updatedUser).hasFieldOrPropertyWithValue("name", newUser.getName());
        assertThat(updatedUser).hasFieldOrPropertyWithValue("login", newUser.getLogin());
        assertThat(updatedUser).hasFieldOrPropertyWithValue("birthday", newUser.getBirthday());
        assertThat(updatedUser).hasFieldOrPropertyWithValue("email", newUser.getEmail());
    }

}
