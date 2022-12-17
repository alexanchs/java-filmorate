package ru.yandex.practicum.filmorate;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
class FilmTests {
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    public void emptyNameFilmValidationTest() {
        final Film film = new Film(1, "", "film", LocalDate.of(1994, 2, 23), 120);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void maxSizeDescriptionFilmValidationTest() {
        String stringMaxSize = RandomString.make(201);
        final Film film = new Film(1, "alexFilm", stringMaxSize, LocalDate.of(1994, 2, 23), 120);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void dateFilmValidationTest() {
        final Film film = new Film(1, "alexFilm", "film", LocalDate.of(1895, 12, 27), 120);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void durationFilmValidationTest() {
        final Film film = new Film(1, "alexFilm", "film", LocalDate.of(1895, 12, 28), -120);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void allOkFilmValidationTest() {
        final Film film = new Film(1, "alexFilm", "film", LocalDate.of(1895, 12, 28), 120);
        Set<ConstraintViolation<Film>> validates = validator.validate(film);
        Assertions.assertEquals(0, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }
}