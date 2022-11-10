package org.example.testing.dao;

import org.example.testing.domain.QuestionWithAnswer;

import java.util.List;

public interface QuestionDao {

    List<QuestionWithAnswer> getQuestionsAndAnswers();
}
