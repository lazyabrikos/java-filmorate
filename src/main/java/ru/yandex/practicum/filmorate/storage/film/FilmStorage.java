package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> findAll();

    Film create(Film film);

    Film update(Film newFilm);

    Film addLike(long id, long userId);

    Film getFilm(long id);

    Film deleteLike(long id, long userId);

    Collection<Film> getPopularFilms(long count);
}
