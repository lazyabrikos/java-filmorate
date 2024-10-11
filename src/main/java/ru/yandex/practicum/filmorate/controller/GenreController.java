package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Genre> getAll() {
        logger.info("Пришел запрос GET /genres");
        Collection<Genre> response = genreService.getAll();
        logger.info("Отправлен ответ GET /genres с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getById(@PathVariable long id) {
        logger.info("Пришел запрос GET /genres/{}", id);
        Genre response = genreService.getById(id);
        logger.info("Отправлен ответ GET /genres/{} с телом {}", id, response);
        return response;
    }
}
