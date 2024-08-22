package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpacesValidator implements ConstraintValidator<NoSpaces, String> {

    @Override
    public void initialize(NoSpaces constraintAnnotation) {
        // Инициализация, если требуется
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }
        return !value.contains(" ");
    }
}
