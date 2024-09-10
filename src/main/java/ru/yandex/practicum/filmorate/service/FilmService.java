package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage filmStorage;


    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    public Film getFilm(long id) {
        return filmStorage.getFilm(id);
    }

    public Film addLike(long id, long userId) {
        return filmStorage.addLike(id, userId);
    }

    public Film deleteLike(long id, long userId) {
        return filmStorage.deleteLike(id, userId);
    }

    public Collection<Film> getPopularFilms(long count) {
        return filmStorage.getPopularFilms(count);
    }

}
