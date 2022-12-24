package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

@Data
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

    private Set<Long> friends;

    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
