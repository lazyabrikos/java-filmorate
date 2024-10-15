package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MpaServiceTest {

    private final MpaService mpaService;

    @Test
    public void getMpaByIdTest() {
        Mpa mpa = mpaService.getById(1L);

        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    public void getAllMpasTest() {
        Collection<Mpa> mpas = mpaService.getAll();

        assertEquals(mpas.size(), 5);
    }
}
