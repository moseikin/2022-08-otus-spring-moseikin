package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Genre;
import org.example.library.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(long id) {

        return genreRepository.getGenreById(id);
    }
}
