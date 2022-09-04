package org.example.homework01.dao;

import org.example.homework01.domain.QuestionWithAnswer;

import java.util.List;

public interface QuestionDao {

    List<QuestionWithAnswer> getQuestionsAndAnswers();
}
