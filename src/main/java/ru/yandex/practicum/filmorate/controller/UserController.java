package ru.yandex.practicum.filmorate.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Ошибка валидации!");
        }
        return userService.createUser(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Ошибка валидации!");
        }
        return userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public User getById(@PathVariable(required = false) int userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public Set<User> getFriends(@PathVariable int id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public Set<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException exception) {
        return new ErrorResponse("Ошибка валидации!", exception.getMessage());
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
