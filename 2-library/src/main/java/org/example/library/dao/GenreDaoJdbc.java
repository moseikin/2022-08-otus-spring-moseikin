package org.example.library.dao;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Genre getGenreById(long id) {

        return namedParameterJdbcOperations.queryForObject("select genre_id, genre_name from genres where genre_id = :id",
                Map.of("id", id), new GenreRowMapper());
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("genre_id");
            String name = rs.getString("genre_name");

            return new Genre(id, name);
        }
    }
}
