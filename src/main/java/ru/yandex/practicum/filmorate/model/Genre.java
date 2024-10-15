package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    public Genre(Long id) {
        this.id = id;
    }
}
