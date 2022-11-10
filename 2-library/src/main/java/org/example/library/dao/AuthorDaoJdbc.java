package org.example.library.dao;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Author getAuthorById(long id) {
        return namedParameterJdbcOperations.queryForObject("select author_id, author_surname, author_name " +
                        "from authors where author_id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("author_id");
            String surname = rs.getString("author_surname");
            String name = rs.getString("author_name");

            return new Author(id, surname, name);
        }
    }
}
