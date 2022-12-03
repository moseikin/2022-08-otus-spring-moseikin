package org.example.library.service;

import org.example.library.dto.BookRequestDto;
import org.example.library.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    Long createBook(BookRequestDto bookRequestDto);

    BookResponseDto getBook(long id);

    Long updateBook(BookRequestDto bookRequestDto);

    void deleteBookById(long id);

    List<BookResponseDto> getAllBooks();
}
