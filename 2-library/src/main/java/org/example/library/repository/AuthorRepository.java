package org.example.library.repository;

import org.example.library.domain.Author;

public interface AuthorRepository {

    Author getAuthorById(long id);
}
