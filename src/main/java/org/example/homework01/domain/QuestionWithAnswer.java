package org.example.homework01.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionWithAnswer {

    private final String questionText;

    private final List<AnswerVariant> answerVariants;
}
