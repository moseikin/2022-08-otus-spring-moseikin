package org.example.library.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookRequestDto {

    private final Long bookId;

    private final String bookName;

    private final long authorId;

    private final long genreId;
}
