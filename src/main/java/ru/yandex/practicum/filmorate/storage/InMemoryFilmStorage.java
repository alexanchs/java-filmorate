package ru.yandex.practicum.filmorate.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}
