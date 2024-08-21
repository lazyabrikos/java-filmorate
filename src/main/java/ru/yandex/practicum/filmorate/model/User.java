package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.NoSpaces;

import java.time.LocalDate;

@Data
public class User {

    private Long id;

    @NotBlank
    @Email(message = "Wrong format of email")
    private String email;

    @NotBlank(message = "Login could not be blank.")
    @NoSpaces(message = "Login could not contain spaces")
    private String login;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Birthday could not be in future.")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
