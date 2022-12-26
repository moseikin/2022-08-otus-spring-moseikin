package org.example.library.repository;

import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Comment;
import org.example.library.domain.Genre;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final long BOOK_ID_WITH_COMMENTS = 1L;
    private static final int COMMENTS_COUNT = 3;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void createComment() {
        long newCommentId = 4L;

        Book expectedBook = new Book(BOOK_ID_WITH_COMMENTS, "test book 1", getExpectedAuthor1(), getExpectedGenre1());
        Comment expectedComment = new Comment(newCommentId, expectedBook, "test comment 4");

        commentRepositoryJpa.createComment(new Comment(null, expectedBook, "test comment 4"));
        Comment actualComment = testEntityManager.find(Comment.class, newCommentId);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void getById() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        Book expectedBook = new Book(1L, "test name 1", getExpectedAuthor1(), getExpectedGenre1());
        Comment expectedComment = new Comment(1L, expectedBook, "first test comment to first book");

        Comment actualComment = commentRepositoryJpa.getById(1L);

        assertThat(actualComment)
                .matches(c -> c.getCommentId().equals(expectedComment.getCommentId()), "")
                .matches(c -> c.getBook().getGenre().getName().equals(expectedComment.getBook().getGenre().getName()), "")
                .matches(c -> c.getBook().getAuthor().getId().equals(expectedComment.getBook().getAuthor().getId()), "")
                .matches(c -> c.getBook().getAuthor().getSurname().equals(expectedComment.getBook().getAuthor().getSurname()), "")
                .matches(c -> c.getBook().getAuthor().getName().equals(expectedComment.getBook().getAuthor().getName()), "")
                .matches(c -> c.getContent().equals(expectedComment.getContent()), "");

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void getAllCommentsByBookId() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Comment> actualComments = commentRepositoryJpa.getAllCommentsByBookId(1L);

        assertThat(actualComments).isNotNull()
                .hasSize(COMMENTS_COUNT)
                .allMatch(ac -> ac.getContent() != null)
                .allMatch(ac -> !ac.getContent().equals(""))
                .allMatch(ac -> ac.getBook() != null)
                .allMatch(ac -> ac.getBook().getId() != null)
                .allMatch(ac -> !ac.getBook().getBookName().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void updateComment() {
        Comment oldComment = testEntityManager.find(Comment.class, 3L);
        Comment expectedComment = new Comment(oldComment.getCommentId(),
                new Book(2L, "test book 2", getExpectedAuthor2(), getExpectedGenre2()),
                "updated comment for second book");

        testEntityManager.detach(oldComment);

        Comment actualComment = commentRepositoryJpa.updateComment(expectedComment);

        assertThat(actualComment).usingRecursiveComparison().isNotEqualTo(oldComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void deleteById() {
        List<Comment> commentCountBeforeDeleting = commentRepositoryJpa.getAllCommentsByBookId(BOOK_ID_WITH_COMMENTS);

        assertThat(commentCountBeforeDeleting).hasSize(COMMENTS_COUNT);

        long commentIdToDelete = 1L;

        commentRepositoryJpa.deleteById(commentIdToDelete);

        List<Comment> commentCountAfterDeleting = commentRepositoryJpa.getAllCommentsByBookId(BOOK_ID_WITH_COMMENTS);

        assertThat(commentCountAfterDeleting).hasSize(COMMENTS_COUNT - 1)
                .allMatch(c -> c.getCommentId() != commentIdToDelete);
    }

    private Author getExpectedAuthor1() {

        return new Author(1L, "author surname 1", "author name 1");
    }

    private Genre getExpectedGenre1() {

        return new Genre(1L, "test genre 1");
    }

    private Author getExpectedAuthor2() {

        return new Author(2L, "author surname 2", "author name 2");
    }

    private Genre getExpectedGenre2() {

        return new Genre(2L, "test genre 2");
    }
}
