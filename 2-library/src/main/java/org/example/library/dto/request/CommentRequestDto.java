package org.example.library.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    private final Long commentId;

    private final Long bookId;

    private final String content;
}
