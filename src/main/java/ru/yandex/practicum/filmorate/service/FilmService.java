package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IncorrectFieldException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikeStorage;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final LikeStorage likeStorage;
    private final MpaService mpaService;
    private final GenreService genreService;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        Mpa mpa;
        try {
            mpa = mpaService.getById(film.getMpa().getId());
        } catch (NotFoundException e) {
            throw new IncorrectFieldException("Введен неправильный рейтинг MPA");
        }
        Set<Long> genresIds = film.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        List<Genre> genres = genreService.getGenresByIds(genresIds);

        if (genres.size() != genresIds.size()) {
            throw new IncorrectFieldException("Введен неправильный жанр");
        }

        film = filmStorage.create(film);
        genreService.insertFilmAndGenres(film.getId(), genresIds);
        film.setMpa(mpa);
        film.setGenres(new LinkedHashSet<>(genres));
        return film;

    }

    public Film update(Film newFilm) {
        Film savedFilm = getFilm(newFilm.getId());
        Mpa mpa;
        try {
            mpa = mpaService.getById(newFilm.getMpa().getId());
        } catch (NotFoundException e) {
            throw new IncorrectFieldException("Введен неправильный рейтинг MPA");
        }

        Set<Long> genresIds = newFilm.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        List<Genre> genres = genreService.getGenresByIds(genresIds);

        if (genresIds.size() != genres.size()) {
            throw new IncorrectFieldException("Введен неправильный жанр");
        }

        genreService.update(newFilm.getId(), genresIds);
        newFilm = filmStorage.update(newFilm);
        newFilm.setGenres(new LinkedHashSet<>(genres));
        newFilm.setMpa(mpa);
        return newFilm;
    }

    public Film getFilm(long id) {
        Film film = filmStorage.getFilm(id);
        Mpa mpa;
        try {
            mpa = mpaService.getById(film.getMpa().getId());
        } catch (NotFoundException e) {
            throw new IncorrectFieldException("Введен неправильный рейтинг MPA");
        }
        Collection<Genre> genres = genreService.getGenresForOneFilm(film.getId());
        film.setGenres(new LinkedHashSet<>(genres));
        film.setMpa(mpa);
        return film;
    }

    public Film addLike(long id, long userId) {
        User user = userService.getUser(userId);
        Film film = filmStorage.getFilm(id);
        likeStorage.addLike(userId, id);
        return film;
    }

    public Film deleteLike(long id, long userId) {
        User user = userService.getUser(userId);
        Film film = filmStorage.getFilm(id);
        likeStorage.deleteLike(userId, id);
        return film;
    }

    public Collection<Film> getPopularFilms(long count) {
        return filmStorage.getPopularFilms(count);
    }

}
