package org.example.library.repository;

import org.example.library.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void getGenreById() {
        long genreId = 1L;

        Genre expectedGenre = new Genre(genreId, "test genre 1");

        Genre actualGenre = genreRepository.findById(genreId).orElse(new Genre());

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getGenreById_ShouldReturnEmptyOptional() {
        long genreId = Long.MAX_VALUE;

        Optional<Genre> actualGenreOptional = genreRepository.findById(genreId);

        assertThat(actualGenreOptional).isEmpty();
    }
}
