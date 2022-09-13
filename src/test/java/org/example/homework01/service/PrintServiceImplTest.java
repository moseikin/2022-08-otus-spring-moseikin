package org.example.homework01.service;

import org.example.homework01.dao.QuestionDao;
import org.example.homework01.dao.QuestionDaoImpl;
import org.example.homework01.domain.QuestionWithAnswer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrintServiceImplTest {

    private final PrintServiceImpl printService = new PrintServiceImpl();
    private QuestionDao questionDao;

    @Test
    void print() {
        questionDao = new QuestionDaoImpl("/qna.csv");

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printService.print(questionWithAnswers.toString());

        assertNotNull(outContent);
    }

    @Test
    void printQuestionAndAnswers_Should_PrintNothing() {
        questionDao = new QuestionDaoImpl("/wrong.file");
        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();
        printService.print(questionWithAnswers.toString());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertEquals("", outContent.toString());
    }
}
