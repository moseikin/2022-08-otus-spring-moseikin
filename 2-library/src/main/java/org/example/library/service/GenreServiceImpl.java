package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.dao.GenreDao;
import org.example.library.domain.Genre;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre getGenreById(long id) {

        return genreDao.getGenreById(id);
    }
}
