package org.example.library.service;

import org.example.library.dto.request.BookRequestDto;
import org.example.library.dto.response.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto bookRequestDto);

    BookResponseDto getBook(long id);

    BookResponseDto updateBook(BookRequestDto bookRequestDto);

    void deleteBook(long id);

    List<BookResponseDto> getAllBooks();
}
