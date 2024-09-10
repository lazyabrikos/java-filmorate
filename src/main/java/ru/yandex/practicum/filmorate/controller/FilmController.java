package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        return filmService.getFilm(id);
    }

    @GetMapping
    public Collection<Film> findAll() {
        logger.info("Пришел Get запрос всех фильмов");
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        logger.info("Пришел Post запрос /films с телом: {}", film);
        logger.info("Отправлен ответ Post /films с телом: {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        logger.info("Пришел запрос Put /films с телом: {}", newFilm);
        logger.info("Отправлен ответ Put /films с телом: {}", newFilm);
        return filmService.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @PathVariable long id,
            @PathVariable long userId
    ) {

        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(
            @PathVariable long id,
            @PathVariable long userId
    ) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") long count
    ) {
        return filmService.getPopularFilms(count);
    }
}
