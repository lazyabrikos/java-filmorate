package ru.yandex.practicum.filmorate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private static final Logger logger = LoggerFactory.getLogger(MpaController.class);
    private final MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Mpa> getAll() {
        logger.info("Пришел запрос GET /mpa");
        Collection<Mpa> response = mpaService.getAll();
        logger.info("Отправлен ответ GET /mpa с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mpa getById(@PathVariable long id) {
        logger.info("Пришел запрос GET /mpa/{}", id);
        Mpa response = mpaService.getById(id);
        logger.info("Отправлен ответ GET /mpa/{} с телом {}", id, response);
        return response;
    }
}
