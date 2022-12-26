package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Author;
import org.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(long id) {
        return authorRepository.getAuthorById(id);
    }
}
