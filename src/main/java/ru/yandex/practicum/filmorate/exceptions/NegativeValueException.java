package ru.yandex.practicum.filmorate.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegativeValueException extends IllegalArgumentException {
    private String error;
    private String description;

    public NegativeValueException(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
