package org.example.testing.dao;

import org.example.testing.component.LocaleProvider;
import org.example.testing.domain.QuestionWithAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class QuestionDaoImplTest {

    private QuestionDaoImpl questionDao;

    @MockBean
    private LocaleProvider localeProvider;

    @Test
    void getQuestionsAndAnswers_WithRussianLocale() {

        Mockito.when(localeProvider.getLocale())
                .thenReturn(new Locale("ru_RU"));

        questionDao = new QuestionDaoImpl("/qna.csv", localeProvider);

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertTrue(questionWithAnswers.size() > 0);
    }

    @Test
    void getQuestionsAndAnswers_WithEnglishLocale() {
        Mockito.when(localeProvider.getLocale())
                .thenReturn(new Locale("en"));

        questionDao = new QuestionDaoImpl("/qna.csv", localeProvider);

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertTrue(questionWithAnswers.size() > 0);
    }

    @Test
    void getQuestionsAndAnswers_WithAbsentLocale_Should_ReturnEmptyList() {
        Mockito.when(localeProvider.getLocale())
                .thenReturn(new Locale("zz"));

        questionDao = new QuestionDaoImpl("/qna.csv", localeProvider);

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertTrue(questionWithAnswers.isEmpty());
    }

    @Test
    void getQuestionsAndAnswers_Should_ReturnEmptyList() {
        questionDao = new QuestionDaoImpl("/wrong.file", null);

        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        assertEquals(0, questionWithAnswers.size());
    }
}
