package org.example.library.service;

import org.example.library.dto.BookResponseDto;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public String getBookInfo(BookResponseDto dto) {

        return dto.getId() + ": " + dto.getBookName() + ", " + dto.getAuthorNameWithSurname() + ", " + dto.getGenreName();
    }
}
