package org.example.library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private final Book book;

    @Column(name = "content")
    private final String content;
}
