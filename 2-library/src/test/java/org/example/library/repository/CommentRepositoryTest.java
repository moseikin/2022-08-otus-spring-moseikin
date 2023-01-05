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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    private static final long BOOK_ID_WITH_COMMENTS = 1L;
    private static final int COMMENTS_COUNT = 3;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void createComment() {
        long newCommentId = 4L;

        Book expectedBook = new Book(BOOK_ID_WITH_COMMENTS, "test book 1", getExpectedAuthor1(), getExpectedGenre1());
        Comment expectedComment = new Comment(newCommentId, expectedBook, "test comment 4");

        commentRepository.save(new Comment(null, expectedBook, "test comment 4"));
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

        Comment actualComment = commentRepository.findById(1L).orElse(new Comment());

        assertThat(actualComment)
                .matches(c -> c.getId().equals(expectedComment.getId()))
                .matches(c -> c.getBook().getId() != null)
                .matches(c -> c.getContent().equals(expectedComment.getContent()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void getAllCommentsByBookId() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Comment> actualComments = commentRepository.getAllCommentsByBookId(1L);

        assertThat(actualComments).isNotNull()
                .hasSize(COMMENTS_COUNT)
                .allMatch(ac -> ac.getContent() != null)
                .allMatch(ac -> !ac.getContent().equals(""))
                .allMatch(ac -> ac.getBook() != null)
                .allMatch(ac -> ac.getBook().getId() != null)
                .allMatch(ac -> !ac.getBook().getBookName().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(2L);
        sessionFactory.getStatistics().setStatisticsEnabled(false);
        sessionFactory.getStatistics().clear();
    }

    @Test
    void updateComment() {
        Comment oldComment = testEntityManager.find(Comment.class, 3L);
        Comment expectedComment = new Comment(oldComment.getId(),
                new Book(2L, "test book 2", getExpectedAuthor2(), getExpectedGenre2()),
                "updated comment for second book");

        testEntityManager.detach(oldComment);

        Comment actualComment = commentRepository.save(expectedComment);

        assertThat(actualComment).usingRecursiveComparison().isNotEqualTo(oldComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void deleteById() {
        List<Comment> commentCountBeforeDeleting = commentRepository.getAllCommentsByBookId(BOOK_ID_WITH_COMMENTS);

        assertThat(commentCountBeforeDeleting).hasSize(COMMENTS_COUNT);

        long commentIdToDelete = 1L;

        commentRepository.deleteById(commentIdToDelete);

        List<Comment> commentCountAfterDeleting = commentRepository.getAllCommentsByBookId(BOOK_ID_WITH_COMMENTS);

        assertThat(commentCountAfterDeleting).hasSize(COMMENTS_COUNT - 1)
                .allMatch(c -> c.getId() != commentIdToDelete);
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
