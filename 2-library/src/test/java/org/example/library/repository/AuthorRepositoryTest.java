package org.example.library.repository;

import org.example.library.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void getAuthorById() {
        long authorId = 1L;

        Author expectedAuthor = new Author(authorId, "author surname 1", "author name 1");

        Author actualAuthor = authorRepository.findById(authorId).orElse(new Author());

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getAuthorById_ShouldReturnEmptyOptional() {
        long authorId = Long.MAX_VALUE;

        Optional<Author> actualAuthorOptional = authorRepository.findById(authorId);

        assertThat(actualAuthorOptional).isEmpty();
    }
}
