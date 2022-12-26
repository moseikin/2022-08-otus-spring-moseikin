package org.example.library.repository;

import org.example.library.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    void getGenreById() {
        long genreId = 1L;

        Genre expectedGenre = new Genre(genreId, "test genre 1");

        Genre actualGenre = genreRepositoryJpa.getGenreById(genreId);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
