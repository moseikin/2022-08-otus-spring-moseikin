package org.example.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionWithAnswer {

    private final String questionText;

    private final List<AnswerVariant> answerVariants;
}
