package org.example.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerVariant {

    private final String text;

    private final boolean isCorrect;
}
