package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.dao.BookDao;
import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.example.library.dto.BookRequestDto;
import org.example.library.dto.BookResponseDto;
import org.example.library.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    @Override
    public Long createBook(BookRequestDto bookRequestDto) {

        Author author = authorService.getAuthorById(bookRequestDto.getAuthorId());

        Genre genre = genreService.getGenreById(bookRequestDto.getGenreId());

        Book book = new Book(null, bookRequestDto.getBookName(), author, genre);

        return bookDao.insert(book);
    }

    @Override
    public BookResponseDto getBook(long id) {
        Book book = bookDao.getById(id);

        return bookMapper.toBookDto(book);
    }

    @Override
    public Long updateBook(BookRequestDto bookRequestDto) {

        boolean isBookExists = bookDao.isBookExists(bookRequestDto.getBookId());

        if (isBookExists) {
            Author author = authorService.getAuthorById(bookRequestDto.getAuthorId());

            Genre genre = genreService.getGenreById(bookRequestDto.getGenreId());

            return bookDao.updateBook(new Book(bookRequestDto.getBookId(), bookRequestDto.getBookName(), author, genre));
        } else {

            return null;
        }
    }

    @Override
    public void deleteBookById(long id) {

        bookDao.deleteById(id);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookDao.getAll();

        return books.stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }
}
