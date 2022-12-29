package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage {
    int generateId();

    Collection<Film> findAll();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void addLike(int idUser, int idFilm);

    void deleteLike(int idUser, int idFilm);

    Set<Film> getPopularFilms(int limitSize);

    Film getById(int filmId);
}
