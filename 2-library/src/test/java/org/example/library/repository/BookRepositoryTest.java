package org.example.library.repository;

import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class BookRepositoryTest {

    private static final long BOOK_ID_1 = 1L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void insert() {
        long newBookId = 3L;

        Book book = testEntityManager.find(Book.class, newBookId);
        assertThat(book).isNull();

        Author expectedAuthor = getExpectedAuthor1();
        Genre expectedGenre = getExpectedGenre1();

        String newBookName = "new book";

        Book expectedBook = new Book(newBookId, newBookName, expectedAuthor, expectedGenre);

        Book actualBook = bookRepository.save(new Book(null, newBookName, expectedAuthor, expectedGenre));

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getById() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        Book expectedBook = new Book(BOOK_ID_1, "test name 1", getExpectedAuthor1(), getExpectedGenre1());

        Book actualBook = bookRepository.findById(BOOK_ID_1).orElse(new Book());

        assertAll(() -> assertThat(actualBook.getId()).isEqualTo(expectedBook.getId()),
                () -> assertThat(actualBook.getBookName()).isEqualTo(expectedBook.getBookName()),
                () -> assertThat(actualBook.getAuthor().getId()).isEqualTo(expectedBook.getAuthor().getId()),
                () -> assertThat(actualBook.getAuthor().getName()).isEqualTo(expectedBook.getAuthor().getName()),
                () -> assertThat(actualBook.getAuthor().getSurname()).isEqualTo(expectedBook.getAuthor().getSurname()),
                () -> assertThat(actualBook.getGenre().getName()).isEqualTo(expectedBook.getGenre().getName()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void getAll() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> actualBooks = bookRepository.findAll();

        assertThat(actualBooks).hasSize(2)
                .allMatch(book -> !book.getGenre().getName().isEmpty())
                .allMatch(book -> !book.getAuthor().getName().isEmpty())
                .allMatch(book -> !book.getAuthor().getSurname().isEmpty())
                .allMatch(book -> !book.getBookName().isEmpty());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void updateBook() {
        String newBookName = "new book name";
        Author newAuthor = getExpectedAuthor2();
        Genre newGenre = getExpectedGenre2();
        Book expectedBook = new Book(BOOK_ID_1, newBookName, newAuthor, newGenre);

        bookRepository.save(expectedBook);

        Book actualBook = testEntityManager.find(Book.class, BOOK_ID_1);

        assertThat(actualBook).isNotNull()
                .matches(book -> book.getBookName().equals(newBookName))
                .matches(book -> book.getAuthor().getId().equals(newAuthor.getId()))
                .matches(book -> book.getAuthor().getName().equals(newAuthor.getName()))
                .matches(book -> book.getAuthor().getSurname().equals(newAuthor.getSurname()))
                .matches(book -> book.getGenre().getId().equals(newGenre.getId()))
                .matches(book -> book.getGenre().getName().equals(newGenre.getName()));
    }

    @Test
    void deleteById() {
        Book book = testEntityManager.find(Book.class, BOOK_ID_1);

        assertNotNull(book);

        testEntityManager.detach(book);

        bookRepository.deleteById(BOOK_ID_1);

        assertNull(testEntityManager.find(Book.class, BOOK_ID_1));
    }

    private Author getExpectedAuthor1() {

        return new Author(1L, "author surname 1", "author name 1");
    }

    private Genre getExpectedGenre1() {

        return new Genre(1L, "test genre 1");
    }

    private Author getExpectedAuthor2() {

        return new Author(2L, "author surname 2", "author name 2");
    }

    private Genre getExpectedGenre2() {

        return new Genre(2L, "test genre 2");
    }
}
