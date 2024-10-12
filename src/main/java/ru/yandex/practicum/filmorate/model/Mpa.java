package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {

    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    public Mpa(Long id) {
        this.id = id;
    }
}
