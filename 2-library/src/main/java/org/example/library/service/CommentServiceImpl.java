package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Book;
import org.example.library.domain.Comment;
import org.example.library.dto.request.CommentRequestDto;
import org.example.library.dto.response.CommentResponseDto;
import org.example.library.mapper.CommentMapper;
import org.example.library.repository.BookRepository;
import org.example.library.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final String NEW_COMMENT_WITH_ID = "Новый комментарий не должен иметь идентификатор";
    private static final String UPDATE_COMMENT_WITHOUT_ID = "Нет идентификатора для обновляемого комментария";

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Comment comment = createCommentEntity(commentRequestDto);

        if (commentRequestDto.getCommentId() == null) {

            commentRepository.createComment(comment);

            return commentMapper.toResponseDto(comment);
        } else {

            throw new IllegalStateException(NEW_COMMENT_WITH_ID);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto getComment(long id) {
        Comment comment = commentRepository.getById(id);

        return commentMapper.toResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto) {
        if (commentRequestDto.getCommentId() == null) {

            throw new IllegalStateException(UPDATE_COMMENT_WITHOUT_ID);
        } else {
            Comment comment = createCommentEntity(commentRequestDto);

            comment = commentRepository.updateComment(comment);

            return commentMapper.toResponseDto(comment);
        }
    }

    private Comment createCommentEntity(CommentRequestDto commentRequestDto) {
        Book book = bookRepository.getById(commentRequestDto.getBookId());

        return new Comment(commentRequestDto.getCommentId(), book, commentRequestDto.getContent());
    }

    @Override
    @Transactional
    public void deleteComment(long id) {

        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllCommentsByBookId(long id) {
        List<Comment> comments = commentRepository.getAllCommentsByBookId(id);

        return comments.stream()
                .map(commentMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
