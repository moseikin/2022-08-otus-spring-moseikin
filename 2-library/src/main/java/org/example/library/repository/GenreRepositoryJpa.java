package org.example.library.repository;

import org.example.library.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    public Genre getGenreById(long id) {

        return em.find(Genre.class, id);
    }
}
