package org.example.library.dto.response;

import lombok.Getter;

@Getter
public class BookResponseDto {
    private final long id;

    private final String bookName;

    private final String authorNameWithSurname;

    private final String genreName;

    public BookResponseDto(long id, String bookName, String authorName, String authorSurname, String genreName) {
        this.id = id;
        this.bookName = bookName;
        this.authorNameWithSurname = authorName + " " + authorSurname;
        this.genreName = genreName;
    }
}
