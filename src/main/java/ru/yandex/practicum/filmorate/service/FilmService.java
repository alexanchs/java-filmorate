package ru.yandex.practicum.filmorate.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void addLike(int idUser, int idFilm) {
        filmStorage.addLike(idUser, idFilm);
    }

    public void deleteLike(int idUser, int idFilm) {
        filmStorage.deleteLike(idUser, idFilm);
    }

    public Set<Film> getPopularFilms() {
        return filmStorage.getPopularFilms();
    }
}
