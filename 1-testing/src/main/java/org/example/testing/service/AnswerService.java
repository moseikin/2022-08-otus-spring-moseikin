package org.example.testing.service;

import org.example.testing.domain.AnswerVariant;

import java.util.List;

public interface AnswerService {

    int getAnswer(List<AnswerVariant> answerVariants);
}
