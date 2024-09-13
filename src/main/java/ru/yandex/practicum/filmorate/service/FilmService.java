package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;


    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
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
        User user = userService.getUser(userId);
        Film film = filmStorage.getFilm(id);
        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(long id, long userId) {
        User user = userService.getUser(userId);
        Film film = filmStorage.getFilm(id);
        film.getLikes().remove(userId);
        return film;
    }

    public Collection<Film> getPopularFilms(long count) {
        return filmStorage.getPopularFilms(count);
    }

}
