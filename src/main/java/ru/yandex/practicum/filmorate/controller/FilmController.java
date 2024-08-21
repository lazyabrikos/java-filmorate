package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();
    private long idGenerator = 0;

    @GetMapping
    public Collection<Film> findAll() {
        logger.info("Пришел Get запрос всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        logger.info("Пришел Post запрос /films с телом: {}", film);
        film.setId(++idGenerator);
        films.put(film.getId(), film);
        logger.info("Отправлен ответ Post /films с телом: {}", film);
        logger.info("Новый фильм успешно добавлен с id = {}", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        logger.info("Пришел запрос Put /films с телом: {}", newFilm);
        if (films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            logger.info("Отправлен ответ Put /films с телом: {}", newFilm);
            logger.info("Фильм с id = {} успешно обновлен", newFilm.getId());
            return newFilm;
        }

        logger.warn("Фильм с таким id не найдем");
        throw new NotFoundException("No film with id = " + newFilm.getId());
    }
}
