package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.ValidReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {

    private Long id;
    private Set<Long> likes = new HashSet<>();

    @NotBlank(message = "Name could not be blank.")
    private String name;

    @Size(max = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ValidReleaseDate(message = "Release date could not be earlier then December 28, 1985")
    private LocalDate releaseDate;

    @Positive(message = "Duration must be positive.")
    private int duration;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

}
