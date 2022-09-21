package org.example.homework01.dao;

import org.example.homework01.domain.QuestionWithAnswer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionDaoImplTest {

    private QuestionDaoImpl questionDao;

    @Test
    void getQuestionsAndAnswers() {
        questionDao = new QuestionDaoImpl("/qna.csv");

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertTrue(questionWithAnswers.size() > 0);
    }

    @Test
    void getQuestionsAndAnswers_Should_ReturnEmptyList() {
        questionDao = new QuestionDaoImpl("/wrong.file");

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertEquals(0, questionWithAnswers.size());
    }
}
