package ru.yandex.practicum.filmorate.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка валидации!");
        }
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка валидации!");
        }
        return filmService.updateFilm(film);
    }
}
