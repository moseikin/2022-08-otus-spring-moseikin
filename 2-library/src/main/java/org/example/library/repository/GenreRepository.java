package org.example.library.repository;

import org.example.library.domain.Genre;

public interface GenreRepository {

    Genre getGenreById(long id);
}
