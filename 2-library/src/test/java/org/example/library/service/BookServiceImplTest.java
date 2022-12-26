package org.example.library.service;

import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.example.library.dto.request.BookRequestDto;
import org.example.library.dto.response.BookResponseDto;
import org.example.library.mapper.BookMapper;
import org.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class BookServiceImplTest {

    private static final String BOOK_NAME = "test book name";

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorServiceImpl authorService;
    @MockBean
    private GenreServiceImpl genreService;
    @MockBean
    private BookMapper bookMapper;

    @Test
    void createBook() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        BookResponseDto expectedDto = new BookResponseDto(1L, BOOK_NAME,
                author.getName(), author.getSurname(), genre.getName());
        Book savedBook = new Book(1L, BOOK_NAME, author, genre);

        doReturn(author).when(authorService).getAuthorById(author.getId());
        doReturn(genre).when(genreService).getGenreById(genre.getId());
        doReturn(savedBook).when(bookRepository).save(any());
        doReturn(expectedDto).when(bookMapper).toResponseDto(savedBook);

        BookRequestDto bookRequestDto = new BookRequestDto(null, BOOK_NAME, author.getId(), genre.getId());
        BookResponseDto actualDto = bookService.createBook(bookRequestDto);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void createBook_ShouldThrowIllegalStateException() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        BookRequestDto bookRequestDto = new BookRequestDto(1L, BOOK_NAME, author.getId(), genre.getId());

        assertThrows(IllegalStateException.class, () -> bookService.createBook(bookRequestDto));
    }

    @Test
    void getBook() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        BookResponseDto expectedDto = new BookResponseDto(1L, BOOK_NAME,
                author.getName(), author.getSurname(), genre.getName());

        Book savedBook = new Book(1L, BOOK_NAME, author, genre);

        doReturn(expectedDto).when(bookMapper).toResponseDto(savedBook);
        doReturn(Optional.of(savedBook)).when(bookRepository).findById(savedBook.getId());

        assertThat(bookService.getBook(1L)).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void updateBook() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        BookResponseDto expectedDto = new BookResponseDto(1L, BOOK_NAME,
                author.getName(), author.getSurname(), genre.getName());

        Book book = new Book(1L, BOOK_NAME, author, genre);

        doReturn(author).when(authorService).getAuthorById(author.getId());
        doReturn(genre).when(genreService).getGenreById(genre.getId());
        doReturn(book).when(bookRepository).save(any());
        doReturn(expectedDto).when(bookMapper).toResponseDto(book);

        BookRequestDto bookRequestDto = new BookRequestDto(1L, BOOK_NAME, author.getId(), genre.getId());
        BookResponseDto actualDto = bookService.updateBook(bookRequestDto);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void updateBook_ShouldThrowIllegalStateException() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        BookRequestDto bookRequestDto = new BookRequestDto(null, BOOK_NAME, author.getId(), genre.getId());

        assertThrows(IllegalStateException.class, () -> bookService.updateBook(bookRequestDto));
    }

    @Test
    void getAllBooks() {
        Author author1 = getExpectedAuthor1();
        Genre genre1 = getExpectedGenre1();
        Author author2 = getExpectedAuthor2();
        Genre genre2 = getExpectedGenre2();

        String secondBookName = "other book";

        BookResponseDto expectedDto1 = new BookResponseDto(1L, BOOK_NAME, author1.getName(),
                author1.getSurname(), genre1.getName());
        BookResponseDto expectedDto2 = new BookResponseDto(2L, secondBookName, author2.getName(),
                author2.getSurname(), genre2.getName());

        List<BookResponseDto> expectedDtos = Arrays.asList(expectedDto1, expectedDto2);

        Book book1 = new Book(1L, BOOK_NAME, author1, genre1);
        Book book2 = new Book(2L, secondBookName, author2, genre2);
        List<Book> books = Arrays.asList(book1, book2);

        doReturn(author1).when(authorService).getAuthorById(author1.getId());
        doReturn(genre1).when(genreService).getGenreById(genre1.getId());
        doReturn(author2).when(authorService).getAuthorById(author2.getId());
        doReturn(genre2).when(genreService).getGenreById(genre2.getId());
        doReturn(books).when(bookRepository).findAll();
        doReturn(expectedDto1).when(bookMapper).toResponseDto(book1);
        doReturn(expectedDto2).when(bookMapper).toResponseDto(book2);

        List<BookResponseDto> actualDtos = bookService.getAllBooks();

        assertThat(actualDtos).usingRecursiveComparison().isEqualTo(expectedDtos);
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
