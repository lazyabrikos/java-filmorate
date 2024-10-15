package ru.yandex.practicum.filmorate.errors;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectFieldException;
import ru.yandex.practicum.filmorate.exceptions.NegativeValueException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        log.error("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ErrorResponse("Not found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final MethodArgumentNotValidException e) {
        log.error("Получен статус 400 Bad request {}", e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ErrorResponse("Validation error", errors);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, NegativeValueException.class,
            IncorrectFieldException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMismatchType(final Exception e) {
        log.error("Получен статус 400 Bad request {}", e.getMessage(), e);
        return new ErrorResponse("Bad request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerErrors(final Exception e) {
        log.error("Получен статус 500 Internal server error {}", e.getMessage(), e);
        return new ErrorResponse("Something went wrong", e.getMessage());
    }


}
