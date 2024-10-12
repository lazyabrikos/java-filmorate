package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;


    public Mpa getById(Long id) {
        return mpaStorage.getMpaById(id);
    }

    public Collection<Mpa> getAll() {
        return mpaStorage.getAll();
    }
}
