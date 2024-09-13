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
        logger.info("Пришел запрос GET /films/{}", id);
        logger.info("Пришел запрос GET /films/{}", id);
        Film response = filmService.getFilm(id);
        logger.info("Отправлен ответ GET /films/{} с телом {}", id, response);
        return response;
    }

    @GetMapping
    public Collection<Film> findAll() {
        logger.info("Пришел запрос GET /films");
        Collection<Film> response = filmService.findAll();
        logger.info("Отправлен ответ GET /films с телом {}", response);
        return response;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        logger.info("Пришел запрос POST /films с телом: {}", film);
        Film response = filmService.create(film);
        logger.info("Отправлен ответ POST /films с телом: {}", response);
        return response;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        logger.info("Пришел запрос PUT /films с телом: {}", newFilm);
        Film response = filmService.update(newFilm);
        logger.info("Отправлен ответ PUT /films с телом: {}", response);
        return response;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable long id, @PathVariable long userId) {
        logger.info("Пришел запрос PUT /films/{}/like/{}", id, userId);
        Film response = filmService.addLike(id, userId);
        logger.info("Отправлен PUT ответ /films/{}/like/{} с телом {}", id, userId, response);
        return response;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable long id, @PathVariable long userId) {
        logger.info("Пришел запрос DELETE /films/{}/like/{}", id, userId);
        Film response = filmService.deleteLike(id, userId);
        logger.info("Отправлен ответ DELETE /films/{}/like/{} с телом {}", id, userId, response);
        return response;
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") long count) {
        logger.info("Пришел запрос PUT /films/popular с параметром count = {}", count);
        Collection<Film> response = filmService.getPopularFilms(count);
        logger.info("Отправлен ответ PUT /films/popular с телом {}", response);
        return response;
    }
}
