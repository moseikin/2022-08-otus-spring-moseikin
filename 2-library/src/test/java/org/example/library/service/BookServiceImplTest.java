package org.example.library.service;

import org.example.library.dao.BookDaoJdbc;
import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.example.library.dto.BookRequestDto;
import org.example.library.dto.BookResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class BookServiceImplTest {

    private static final long BOOK_ID = 1L;

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void createBook() {
        BookRequestDto bookRequestDto = new BookRequestDto(null, "TEST", 1L, 1L);

        doReturn(BOOK_ID).when(bookDaoJdbc).insert(any());

        long bookId = bookService.createBook(bookRequestDto);

        assertEquals(BOOK_ID, bookId);
    }

    @Test
    void getBook() {
        Book book = createFirstTestBook();

        doReturn(book).when(bookDaoJdbc).getById(BOOK_ID);

        BookResponseDto bookResponseDto = bookService.getBook(BOOK_ID);

        assertNotNull(bookResponseDto);
    }

    @Test
    void getBook_ShouldThrowEmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(bookDaoJdbc).getById(BOOK_ID);

        assertThrows(EmptyResultDataAccessException.class, () -> bookService.getBook(BOOK_ID));
    }

    @Test
    void updateBook() {
        Book updatedBook = createFirstTestBook();
        updatedBook.setId(BOOK_ID);
        updatedBook.setBookName("another name");

        doReturn(true).when(bookDaoJdbc).isBookExists(BOOK_ID);
        doReturn(BOOK_ID).when(bookDaoJdbc).updateBook(any());

        BookRequestDto updatedBookRequestDto = new BookRequestDto(BOOK_ID, "another name", 1, 1);

        Long updatedBookId = bookService.updateBook(updatedBookRequestDto);

        assertEquals(BOOK_ID, updatedBookId);
    }

    @Test
    void getAllBooks() {
        List<Book> books = createTestBooksList();

        List<BookResponseDto> expectedBookResponseDtos = createBookResponseDtoList();

        Mockito.when(bookDaoJdbc.getAll())
                .thenReturn(books);

        List<BookResponseDto> actualBookResponseDtos = bookService.getAllBooks();

        assertThat(actualBookResponseDtos).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBookResponseDtos);
    }

    private List<Book> createTestBooksList() {
        return Arrays.asList(createFirstTestBook(), createSecondTestBook());
    }

    private Book createFirstTestBook() {
        Author author = new Author(1L, "Gaiman", "Neil");
        Genre genre = new Genre(1L, "Fantasy");

        return new Book(null, "TEST", author, genre);
    }

    private Book createSecondTestBook() {
        Author author = new Author(1L, "Gaiman", "Neil");
        Genre genre = new Genre(1L, "Fantasy");

        return new Book(null, "another book name", author, genre);
    }

    private List<BookResponseDto> createBookResponseDtoList() {
        return Arrays.asList(createFirstDto(), createSecondDto());
    }

    private BookResponseDto createFirstDto() {
        return new BookResponseDto(1L, "TEST", "Neil", "Gaiman", "Fantasy");
    }

    private BookResponseDto createSecondDto() {
        return new BookResponseDto(2L, "another book name", "Neil", "Gaiman", "Fantasy");
    }
}
