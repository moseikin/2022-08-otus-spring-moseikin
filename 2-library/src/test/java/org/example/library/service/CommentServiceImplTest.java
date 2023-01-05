package org.example.library.service;

import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Comment;
import org.example.library.domain.Genre;
import org.example.library.dto.request.CommentRequestDto;
import org.example.library.dto.response.CommentResponseDto;
import org.example.library.mapper.CommentMapper;
import org.example.library.repository.BookRepository;
import org.example.library.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CommentServiceImplTest {

    private static final long COMMENT_ID = 1L;
    private static final String CONTENT = "test comment";
    private static final long BOOK_ID = 1L;

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookRepository bookRepository;

    @Test
    void createComment() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        CommentRequestDto commentRequestDto = new CommentRequestDto(null, BOOK_ID, CONTENT);

        CommentResponseDto expectedDto = new CommentResponseDto(COMMENT_ID, CONTENT);

        Book book = new Book(BOOK_ID, "test book", author, genre);

        doReturn(expectedDto).when(commentMapper).toResponseDto(any());
        doReturn(Optional.of(book)).when(bookRepository).findById(BOOK_ID);

        CommentResponseDto actualComment = commentService.createComment(commentRequestDto);

        assertThat(actualComment).usingRecursiveComparison().ignoringFields("commentId").isEqualTo(expectedDto);
    }

    @Test
    void createComment_ShouldThrowIllegalStateException() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(1L, BOOK_ID, CONTENT);

        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();
        Book book = new Book(BOOK_ID, "test book", author, genre);
        doReturn(Optional.of(book)).when(bookRepository).findById(BOOK_ID);

        assertThrows(IllegalStateException.class, () -> commentService.createComment(commentRequestDto));
    }

    @Test
    void getComment() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        CommentResponseDto expectedDto = new CommentResponseDto(COMMENT_ID, CONTENT);

        Book book = new Book(BOOK_ID, "test book", author, genre);
        Comment savedComment = new Comment(COMMENT_ID, book, CONTENT);

        doReturn(expectedDto).when(commentMapper).toResponseDto(savedComment);
        doReturn(Optional.of(savedComment)).when(commentRepository).findById(COMMENT_ID);

        CommentResponseDto actualDto = commentService.getComment(COMMENT_ID);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void updateComment() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        CommentRequestDto commentRequestDto = new CommentRequestDto(1L, BOOK_ID, CONTENT);

        CommentResponseDto expectedDto = new CommentResponseDto(COMMENT_ID, CONTENT);

        Book book = new Book(BOOK_ID, "test book", author, genre);
        Comment updatedComment = new Comment(COMMENT_ID, book, CONTENT);

        doReturn(expectedDto).when(commentMapper).toResponseDto(updatedComment);
        doReturn(Optional.of(book)).when(bookRepository).findById(BOOK_ID);
        doReturn(updatedComment).when(commentRepository).save(any());

        CommentResponseDto actualDto = commentService.updateComment(commentRequestDto);

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void updateComment_ShouldThrowIllegalStateException() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(null, BOOK_ID, CONTENT);

        assertThrows(IllegalStateException.class, () -> commentService.updateComment(commentRequestDto));
    }

    @Test
    void getAllCommentsByBookId() {
        Author author = getExpectedAuthor1();
        Genre genre = getExpectedGenre1();

        String secondComment = "other comment";

        CommentResponseDto dto1 = new CommentResponseDto(COMMENT_ID, CONTENT);
        CommentResponseDto dto2 = new CommentResponseDto(2L, secondComment);
        List<CommentResponseDto> expectedDtos = Arrays.asList(dto1, dto2);

        Book book = new Book(BOOK_ID, "test book", author, genre);
        Comment comment1 = new Comment(COMMENT_ID, book, CONTENT);
        Comment comment2 = new Comment(2L, book, secondComment);
        List<Comment> comments = Arrays.asList(comment1, comment2);

        doReturn(dto1).when(commentMapper).toResponseDto(comment1);
        doReturn(dto2).when(commentMapper).toResponseDto(comment2);
        doReturn(comments).when(commentRepository).getAllCommentsByBookId(BOOK_ID);

        List<CommentResponseDto> actualDtos = commentService.getAllCommentsByBookId(BOOK_ID);

        assertThat(actualDtos).usingRecursiveComparison().isEqualTo(expectedDtos);

    }

    private Author getExpectedAuthor1() {

        return new Author(1L, "author surname 1", "author name 1");
    }

    private Genre getExpectedGenre1() {

        return new Genre(1L, "test genre 1");
    }
}
