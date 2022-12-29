package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.DateValidationInterface;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private int id;

    @NotBlank(message = "Имя не должно быть пустым!")
    private final String name;

    @Size(max = 200)
    private final String description;

    @PastOrPresent
    @DateValidationInterface(message = "Дата не должна быть раньше 28 декабря 1895г.")
    private final LocalDate releaseDate;

    @Positive(message = "Продолжительность не должна быть отрицательной!")
    private final int duration;

    private Set<Integer> likes;

    public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Set<Integer> getLikes() {
        if (likes == null) {
            return new HashSet<>();
        }
        return likes;
    }
}
