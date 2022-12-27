package ru.yandex.practicum.filmorate.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;


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
            throw new ValidationException("Ошибка валидации!");
        }
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Ошибка валидации!");
        }
        return filmService.updateFilm(film);
    }

    @GetMapping("/{filmId}")
    @ResponseBody
    public Film getById(@PathVariable(required = false) int filmId) {
        return filmService.getById(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseBody
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseBody
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    public Set<Film> getPopular(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException exception) {
        return new ErrorResponse("Ошибка валидации!", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final FilmNotFoundException exception) {
        return new ErrorResponse("Фильм не найден!", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final UserNotFoundException exception) {
        return new ErrorResponse("Пользователь не найден!", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(final Exception exception) {
        return new ErrorResponse("Возникло исключение!", exception.getMessage());
    }
}
