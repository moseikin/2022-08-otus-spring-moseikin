package org.example.library.dao;

import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void insert() {
        Book expectedBook = getTestBook();

        Long bookId = bookDaoJdbc.insert(expectedBook);

        Book actualBook = bookDaoJdbc.getById(bookId);

        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    private Book getTestBook() {
        Author author = new Author(1L, "author surname 1", "author name 1");
        Genre genre = new Genre(1L, "test genre 1");

        return new Book(null, "test name 1", author, genre);
    }

    @Test
    void getBook() {
        Book bookToInsert = getTestBook2();

        Book insertedBook = bookDaoJdbc.getById(2L);

        assertThat(bookToInsert)
                .usingRecursiveComparison()
                .isEqualTo(insertedBook);
    }

    private Book getTestBook2() {
        Author author = new Author(2L, "author surname 2", "author name 2");
        Genre genre = new Genre(2L, "test genre 2");

        return new Book(2L, "test name 2", author, genre);
    }

    @Test
    void getBook_ShouldThrowEmptyResultDataAccessException() {

        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbc.getById(Long.MAX_VALUE));
    }

    @Test
    void getAll() {

        assertThat(bookDaoJdbc.getAll().size()).isEqualTo(2);
    }

    @Test
    void updateBook() {
        long bookToUpdateId = 2L;

        Book expectedBook = bookDaoJdbc.getById(bookToUpdateId);
        expectedBook.setBookName("updated book name");

        bookDaoJdbc.updateBook(expectedBook);

        Book actualBook = bookDaoJdbc.getById(bookToUpdateId);

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    void deleteById() {
        assertNotNull(bookDaoJdbc.getById(1L));

        bookDaoJdbc.deleteById(1L);

        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbc.getById(1L));
    }
}
