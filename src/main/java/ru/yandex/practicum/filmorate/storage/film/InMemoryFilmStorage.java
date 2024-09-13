package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NegativeValueException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idGenerator;

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film create(Film film) {
        film.setId(++idGenerator);
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film newFilm) {
        checkFilmAvailability(newFilm.getId());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    public Film getFilm(long id) {
        checkFilmAvailability(id);
        return films.get(id);
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

    public void checkFilmAvailability(long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("No film with id = " + id);
        }
    }
}
