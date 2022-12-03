package org.example.library.dao;

import org.example.library.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void getGenreById() {
        Genre expectedGenre = new Genre(2L, "test genre 2");

        Genre actualGenre = genreDaoJdbc.getGenreById(2L);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getAuthorById_ShouldThrowEmptyResultDataAccessException() {

        assertThrows(EmptyResultDataAccessException.class,
                () -> genreDaoJdbc.getGenreById(1000L));
    }
}
