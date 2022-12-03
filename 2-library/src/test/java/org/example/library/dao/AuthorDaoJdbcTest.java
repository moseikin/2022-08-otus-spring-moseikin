package org.example.library.dao;

import org.example.library.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void getAuthorById() {
        Author expectedAuthor = new Author(1L, "author surname 1", "author name 1");

        Author actualAuthor = authorDaoJdbc.getAuthorById(1L);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getAuthorById_ShouldThrowEmptyResultDataAccessException() {

        assertThrows(EmptyResultDataAccessException.class,
                () -> authorDaoJdbc.getAuthorById(1000L));
    }
}
