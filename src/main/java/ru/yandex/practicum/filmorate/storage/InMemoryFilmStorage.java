package ru.yandex.practicum.filmorate.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGlobal = 0;

    @Override
    public int generateId() {
        idGlobal += 1;
        return idGlobal;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Добавлен фильм: '{}'!", film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Обновлены данные фильма: '{}'!", film.getName());
        } else {
            throw new UserNotFoundException("Нет пользователя с таким id!");
        }
        return film;
    }

    @Override
    public void addLike(int idUser, int idFilm) {
        if (!films.containsKey(idFilm)) {
            throw new FilmNotFoundException(String.format("Фильм с id - %d отсутствует!", idFilm));
        }
        Film film = films.get(idFilm);
        Set<Integer> tempLikes = film.getLikes();
        tempLikes.add(idUser);
        film.setLikes(tempLikes);
        updateFilm(film);
    }

    @Override
    public void deleteLike(int idUser, int idFilm) {
        if (!films.containsKey(idFilm)) {
            throw new FilmNotFoundException(String.format("Фильм с id - %d отсутствует!", idFilm));
        }
        Film film = films.get(idFilm);
        Set<Integer> tempLikes = film.getLikes();
        tempLikes.remove(idUser);
        film.setLikes(tempLikes);
        updateFilm(film);
    }

    @Override
    public Set<Film> getPopularFilms() {
        TreeSet<Film> popularFilms = new TreeSet<>(Comparator.comparingInt(o -> o.getLikes().size()));
        popularFilms.addAll(films.values());
        return popularFilms.stream()
                .limit(10)
                .collect(Collectors.toSet());
    }
}
