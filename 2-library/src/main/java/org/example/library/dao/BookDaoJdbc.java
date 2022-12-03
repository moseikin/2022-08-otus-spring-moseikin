package org.example.library.dao;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Author;
import org.example.library.domain.Book;
import org.example.library.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getBookName());
        params.addValue("author", book.getAuthor().getId());
        params.addValue("genre", book.getGenre().getId());

        namedParameterJdbcOperations.update(
                "insert into books (book_name, author_id, genre_id) values (:name, :author, :genre)",
                params, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Book getById(long id) {

        return namedParameterJdbcOperations.queryForObject(
                "select b.book_id, b.book_name, a.author_id, a.author_surname, a.author_name, g.genre_id, g.genre_name " +
                        "from books b " +
                        "left join authors a on b.author_id = a.author_id " +
                        "left join genres g on b.genre_id = g.genre_id " +
                        "where book_id = :id",
                Map.of("id", id), new BookRowMapper());
    }

    @Override
    public List<Book> getAll() {

        return namedParameterJdbcOperations.query(
                "select b.book_id, b.book_name, a.author_id, a.author_surname, a.author_name, g.genre_id, g.genre_name " +
                        "from books b " +
                        "left join authors a on b.author_id = a.author_id " +
                        "left join genres g on b.genre_id = g.genre_id", new BookRowMapper());
    }

    @Override
    public Long updateBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book_id", book.getId());
        params.addValue("book_name", book.getBookName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "update books set book_name = :book_name, author_id = :author_id, genre_id = :genre_id " +
                        "where book_id = :book_id",
                params, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void deleteById(long id) {

        namedParameterJdbcOperations.update("delete from books where book_id = :id", Map.of("id", id));
    }

    @Override
    public boolean isBookExists(long id) {
        return Objects.requireNonNullElse(namedParameterJdbcOperations.queryForObject(
                        "select exists(select from books where  book_id = :id)", Map.of("id", id),
                        Boolean.class),
                false);
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            String name = rs.getString("book_name");

            Author author = extractAuthor(rs);
            Genre genre = extractGenre(rs);

            return new Book(bookId, name, author, genre);
        }

        private Author extractAuthor(ResultSet rs) throws SQLException {
            long authorId = rs.getLong("author_id");
            String authorSurname = rs.getString("author_surname");
            String authorName = rs.getString("author_name");

            return new Author(authorId, authorSurname, authorName);
        }

        private Genre extractGenre(ResultSet rs) throws SQLException {
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");

            return new Genre(genreId, genreName);
        }
    }
}
