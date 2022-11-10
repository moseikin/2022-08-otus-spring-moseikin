create table if not exists genres
(
    genre_id   bigserial primary key,
    genre_name varchar(100)
);

create table if not exists authors
(
    author_id      bigserial primary key,
    author_surname varchar(100),
    author_name    varchar(100)
);

create table if not exists books
(
    book_id   bigserial primary key,
    book_name varchar(100),
    author_id bigint references authors (author_id),
    genre_id  bigint references genres (genre_id)
);
