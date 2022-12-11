package org.example.library.mapper;

public interface GenericMapper<E, R> {

    R toResponseDto(E entity);
}
