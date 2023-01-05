package org.example.library.repository;

import org.example.library.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "author-genre-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(value = "author-genre-graph")
    Optional<Book> findById(Long aLong);
}
