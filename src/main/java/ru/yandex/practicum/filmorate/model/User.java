package ru.yandex.practicum.filmorate.model;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import net.bytebuddy.build.ToStringPlugin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class User {

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> friends;

    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("email", email);
        values.put("login", login);
        values.put("birthday", birthday);
        return values;
    }
}
