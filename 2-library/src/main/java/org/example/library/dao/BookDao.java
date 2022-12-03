package org.example.library.dao;

import org.example.library.domain.Book;

import java.util.List;

public interface BookDao {

    Long insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    Long updateBook(Book book);

    void deleteById(long id);

    boolean isBookExists(long id);
}
