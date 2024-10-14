package ru.yandex.practicum.filmorate.storage.genres;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreStorage {

    Optional<Genre> getGenreById(long id);

    Collection<Genre> getAll();

    void insertFilmAndGenres(long filmId, Set<Long> genresId);

    void update(long filmId, Set<Long> genresId);

    Collection<Genre> getGenresForFilm(Long filmId);

    List<Genre> getGenresByIds(Set<Long> genresIds);
}
