package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class GenreService {
    private final GenreStorage genreDbStorage;

    public GenreService(GenreStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Genre getById(Long id) {
        return genreDbStorage.getGenreById(id);
    }

    public Collection<Genre> getAll() {
        return genreDbStorage.getAll();
    }

    public void insertFilmAndGenres(Long filmId, Set<Long> genresIds) {
        genreDbStorage.insertFilmAndGenres(filmId, genresIds);
    }

    public void update(Long filmId, Set<Long> genresIds) {
        genreDbStorage.update(filmId, genresIds);
    }

    public Collection<Genre> getGenresForOneFilm(Long filmId) {
        return genreDbStorage.getGenresForFilm(filmId);
    }
}
