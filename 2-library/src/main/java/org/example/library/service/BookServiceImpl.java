package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.example.library.dto.request.BookRequestDto;
import org.example.library.dto.response.BookResponseDto;
import org.example.library.mapper.BookMapper;
import org.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String NEW_BOOK_WITH_ID = "Новая книга не должна иметь идентификатор";
    private static final String UPDATE_BOOK_WITHOUT_ID = "Нет идентификатора для обновляемой книги";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {

        if (bookRequestDto.getBookId() == null) {
            Author author = authorService.getAuthorById(bookRequestDto.getAuthorId());

            Genre genre = genreService.getGenreById(bookRequestDto.getGenreId());

            Book book = new Book(null, bookRequestDto.getBookName(), author, genre);

            Book book1 = bookRepository.insert(book);

            return bookMapper.toResponseDto(book1);
        } else {

            throw new IllegalStateException(NEW_BOOK_WITH_ID);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBook(long id) {
        Book book = bookRepository.getById(id);

        return bookMapper.toResponseDto(book);
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(BookRequestDto bookRequestDto) {

        if (bookRequestDto.getBookId() == null) {

            throw new IllegalStateException(UPDATE_BOOK_WITHOUT_ID);
        } else {
            Author author = authorService.getAuthorById(bookRequestDto.getAuthorId());

            Genre genre = genreService.getGenreById(bookRequestDto.getGenreId());

            Book book = bookRepository.updateBook(new Book(bookRequestDto.getBookId(), bookRequestDto.getBookName(),
                    author, genre));

            return bookMapper.toResponseDto(book);
        }
    }

    @Override
    @Transactional
    public void deleteBook(long id) {

        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.getAll();

        return books.stream().map(bookMapper::toResponseDto).collect(Collectors.toList());
    }
}
