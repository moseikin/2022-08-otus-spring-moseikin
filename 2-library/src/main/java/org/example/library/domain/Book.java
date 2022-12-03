package org.example.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class Book {

    private Long id;

    private String bookName;

    private Author author;

    private Genre genre;
}
