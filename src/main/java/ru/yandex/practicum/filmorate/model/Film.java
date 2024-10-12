package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.ValidReleaseDate;

import java.time.LocalDate;
import java.util.*;

/**
 * Film.
 */
@Data
@NoArgsConstructor
public class Film {

    private Long id;

    @NotBlank(message = "Name could not be blank.")
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ValidReleaseDate(message = "Release date could not be earlier then December 28, 1985")
    private LocalDate releaseDate;

    @Positive(message = "Duration must be positive.")
    private Long duration;

    @NotNull
    private Mpa mpa;

    private Set<Genre> genres = new LinkedHashSet<>();

    public Film(String name, String description, LocalDate releaseDate,
                Long duration, Mpa mpa, Set<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

}
