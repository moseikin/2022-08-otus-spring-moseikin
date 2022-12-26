insert into authors(author_surname, author_name)
values ('author surname 1', 'author name 1');
insert into authors(author_surname, author_name)
values ('author surname 2', 'author name 2');

insert into genres(genre_name)
values ('test genre 1');
insert into genres(genre_name)
values ('test genre 2');

insert into books (book_name, author_id, genre_id)
values ('test name 1', 1, 1);
insert into books (book_name, author_id, genre_id)
values ('test name 2', 2, 2);

insert into comments (book_id, content)
values (1, 'first test comment to first book');

insert into comments (book_id, content)
values (1, 'second test comment to first book');

insert into comments (book_id, content)
values (1, 'third test comment to first book');
