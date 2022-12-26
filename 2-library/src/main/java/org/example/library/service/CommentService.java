package org.example.library.service;

import org.example.library.dto.request.CommentRequestDto;
import org.example.library.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto);

    CommentResponseDto getComment(long id);

    CommentResponseDto updateComment(CommentRequestDto commentRequestDto);

    void deleteComment(long id);

    List<CommentResponseDto> getAllCommentsByBookId(long id);
}
