package org.example.library.repository;

import org.example.library.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryJpa implements BookRepository {

    private static final String AUTHOR_GENRE_COMMENT_GRAPH = "author-genre-graph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book insert(Book book) {
        em.persist(book);

        return book;
    }

    @Override
    public Book getById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph(AUTHOR_GENRE_COMMENT_GRAPH);
        Map<String, Object> properties = new HashMap<>();
        properties.put(getEntityGraphTypeKey(), entityGraph);

        return em.find(Book.class, id, properties);
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph(AUTHOR_GENRE_COMMENT_GRAPH);

        TypedQuery<Book> query = em.createQuery(
                "select b from Book b", Book.class);
        query.setHint(getEntityGraphTypeKey(), entityGraph);

        return query.getResultList();
    }

    private String getEntityGraphTypeKey() {

        return org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH.getKey();
    }

    @Override
    public Book updateBook(Book book) {

        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);

        if (book != null) {
            em.remove(book);
        }
    }
}
