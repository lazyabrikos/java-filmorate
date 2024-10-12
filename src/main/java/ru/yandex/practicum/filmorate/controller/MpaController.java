package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public Collection<Mpa> getAll() {
        log.info("Пришел запрос GET /mpa");
        Collection<Mpa> response = mpaService.getAll();
        log.info("Отправлен ответ GET /mpa с телом {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public Mpa getById(@PathVariable long id) {
        log.info("Пришел запрос GET /mpa/{}", id);
        Mpa response = mpaService.getById(id);
        log.info("Отправлен ответ GET /mpa/{} с телом {}", id, response);
        return response;
    }
}
