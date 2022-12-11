package org.example.library.repository;

import org.example.library.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    @Transactional(readOnly = true)
    void getAuthorById() {
        long authorId = 1L;

        Author expectedAuthor = new Author(authorId, "author surname 1", "author name 1");

        Author actualAuthor = authorRepositoryJpa.getAuthorById(authorId);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
