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

    @GetMapping
    public Collection<Film> findAll() {
        logger.trace("Got request: get all films");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        logger.trace("Got request: add new film");
        film.setId(getNextId());
        films.put(film.getId(), film);
        logger.info("New film successfully added with id = {}", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            logger.info("Film with id = {} successfully updates", newFilm.getId());
            return newFilm;
        }

        logger.warn("No film found with id");
        throw new NotFoundException("No film with id = " + newFilm.getId());
    }


    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
