package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
class UserTests {
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    public void mailUserValidationTest() {
        final User user = new User(1, "Алекс", "яндекс.ру", "алекс", LocalDate.of(1993, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void mailUserValidationNotEmptyTest() {
        final User user = new User(1, "Алекс", "яндекс.ру", "алекс", LocalDate.of(1993, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void loginUserValidationNotEmptyTest() {
        final User user = new User(1, "Алекс", "яндекс@ру", "", LocalDate.of(1993, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(2, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void loginUserValidationNotNotBlankTest() {
        final User user = new User(1, "Алекс", "яндекс@ру", "  ", LocalDate.of(1993, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void birthDayUserValidationTest() {
        final User user = new User(1, "Алекс", "яндекс@mail.ru", "алекс", LocalDate.of(2023, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(1, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    @Test
    public void allOkUserValidationTest() {
        final User user = new User(1, "Алекс", "яндекс@mail.ru", "алекс", LocalDate.of(1993, 2, 21));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        Assertions.assertEquals(0, validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }
}
