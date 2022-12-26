package org.example.library.repository;

import org.example.library.domain.Book;

import java.util.List;

public interface BookRepository {

    Book insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    Book updateBook(Book book);

    void deleteById(long id);
}
