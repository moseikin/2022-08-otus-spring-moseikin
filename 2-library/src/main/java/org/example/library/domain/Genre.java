package org.example.library.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Genre implements Serializable {

    private final Long id;

    private final String name;
}
