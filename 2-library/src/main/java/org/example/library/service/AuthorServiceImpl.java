package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.dao.AuthorDao;
import org.example.library.domain.Author;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getAuthorById(id);
    }
}
