package ru.yandex.practicum.filmorate.storage.likes;

public interface LikeStorage {
    void addLike(Long userId, Long filmId);

    void deleteLike(Long userId, Long filmId);
}
