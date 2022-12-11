package org.example.library.service;

import org.example.library.dto.response.BookResponseDto;
import org.example.library.dto.response.CommentResponseDto;

public interface InfoService {

    String getBookInfo(BookResponseDto bookResponseDto);

    String getCommentInfo(CommentResponseDto commentResponseDto);
}
