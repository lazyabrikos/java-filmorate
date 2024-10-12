package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;


    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info("Пришел запрос GET /films/{}", id);
        Film response = filmService.getFilm(id);
        log.info("Отправлен ответ GET /films/{} с телом {}", id, response);
        return response;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Пришел запрос GET /films");
        Collection<Film> response = filmService.findAll();
        log.info("Отправлен ответ GET /films с телом {}", response);
        return response;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Пришел запрос POST /films с телом: {}", film);
        Film response = filmService.create(film);
        log.info("Отправлен ответ POST /films с телом: {}", response);
        return response;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Пришел запрос PUT /films с телом: {}", newFilm);
        Film response = filmService.update(newFilm);
        log.info("Отправлен ответ PUT /films с телом: {}", response);
        return response;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Пришел запрос PUT /films/{}/like/{}", id, userId);
        Film response = filmService.addLike(id, userId);
        log.info("Отправлен PUT ответ /films/{}/like/{} с телом {}", id, userId, response);
        return response;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Пришел запрос DELETE /films/{}/like/{}", id, userId);
        Film response = filmService.deleteLike(id, userId);
        log.info("Отправлен ответ DELETE /films/{}/like/{} с телом {}", id, userId, response);
        return response;
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") long count) {
        log.info("Пришел запрос GET /films/popular с параметром count = {}", count);
        Collection<Film> response = filmService.getPopularFilms(count);
        log.info("Отправлен ответ GET /films/popular с телом {}", response);
        return response;
    }
}
