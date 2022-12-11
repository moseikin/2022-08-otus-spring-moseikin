package org.example.library.repository;

import org.example.library.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    private static final String COMMENT_BOOK_GRAPH = "comment-book-graph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createComment(Comment comment) {

        em.persist(comment);
    }

    @Override
    public Comment getById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph(COMMENT_BOOK_GRAPH);
        Map<String, Object> properties = new HashMap<>();
        properties.put(getEntityGraphTypeKey(), entityGraph);

        return em.find(Comment.class, id, properties);
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                "from Comment c " +
                "left join Book b on b.id = c.book.id " +
                "where c.book.id = :book_id", Comment.class);

        query.setParameter("book_id", bookId);

        return query.getResultList();
    }

    private String getEntityGraphTypeKey() {

        return org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH.getKey();
    }

    @Override
    public Comment updateComment(Comment comment) {

        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);

        if (comment != null) {
            em.remove(comment);
        }
    }
}
