package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NegativeValueException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idGenerator;
    private final UserStorage userStorage;

    public InMemoryFilmStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film create(Film film) {
        film.setId(++idGenerator);
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            return newFilm;
        }
        log.warn("Фильм с таким id не найдем");
        throw new NotFoundException("No film with id = " + newFilm.getId());
    }

    public Film addLike(long id, long userId) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("No film with id = " + id);
        }

        User user = userStorage.getUser(userId);
        Film film = films.get(id);
        Set<Long> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);

        return film;
    }

    public Film getFilm(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new NotFoundException("No film with id = " + id);
    }

    public Film deleteLike(long id, long userId) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("No film with id = " + id);
        }

        User user = userStorage.getUser(userId);
        Film film = films.get(id);
        Set<Long> likes = film.getLikes();
        likes.remove(userId);
        film.setLikes(likes);
        return film;
    }

    public Collection<Film> getPopularFilms(long count) {
        if (count < 0) {
            throw new NegativeValueException("Validation error  ", "Count could not be negative");
        }
        return films.values().stream()
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLikes().size()).reversed())
                .limit(count)
                .toList();
    }
}
