package org.example.homework01.service;

import org.example.homework01.domain.AnswerVariant;

import java.util.List;

public interface AnswerService {

    int getAnswer(List<AnswerVariant> answerVariants);
}
