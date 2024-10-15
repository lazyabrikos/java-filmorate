package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public Collection<Genre> getAll() {
        log.info("Пришел запрос GET /genres");
        Collection<Genre> response = genreService.getAll();
        log.info("Отправлен ответ GET /genres с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable long id) {
        log.info("Пришел запрос GET /genres/{}", id);
        Genre response = genreService.getById(id);
        log.info("Отправлен ответ GET /genres/{} с телом {}", id, response);
        return response;
    }
}
