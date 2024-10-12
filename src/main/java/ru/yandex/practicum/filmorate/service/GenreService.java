package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre getById(Long id) {
        return genreStorage.getGenreById(id);
    }

    public Collection<Genre> getAll() {
        return genreStorage.getAll();
    }

    public void insertFilmAndGenres(Long filmId, Set<Long> genresIds) {
        genreStorage.insertFilmAndGenres(filmId, genresIds);
    }

    public void update(Long filmId, Set<Long> genresIds) {
        genreStorage.update(filmId, genresIds);
    }

    public Collection<Genre> getGenresForOneFilm(Long filmId) {
        return genreStorage.getGenresForFilm(filmId);
    }

    public List<Genre> getGenresByIds(Set<Long> genreIds) {
        return genreStorage.getGenresByIds(genreIds);
    }
}
