package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private int id;
    private String name;

    @NotEmpty(message = "Почта не должна быть пустой!")
    @Email(message = "Должно иметь формат электронной почты, содержать знак @!")
    private String email;

    @NotEmpty(message = "Логин не должен быть пустым!")
    @NotBlank(message = "Логин не должен состоять из пробелов!")
    private String login;

    @PastOrPresent(message = "Дата рождения не должна быть в будущем!")
    private LocalDate birthday;
}
