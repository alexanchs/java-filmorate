package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.validator.DateValidationInterface;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {

    @EqualsAndHashCode.Exclude
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
}
