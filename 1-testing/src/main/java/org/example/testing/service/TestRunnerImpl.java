package org.example.testing.service;

import org.example.testing.dao.QuestionDao;
import org.example.testing.domain.QuestionWithAnswer;
import org.example.testing.domain.Respondent;
import org.example.testing.domain.TestResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRunnerImpl implements TestRunner {

    private final QuestionDao questionDao;
    private final RespondentService respondentService;
    private final ConverterService converterService;
    private final AnswerService answerService;
    private final PrintService printService;

    private final Integer minCorrect;

    public TestRunnerImpl(QuestionDao questionDao, AnswerService answerService,
                          RespondentService respondentService,
                          ConverterService converterService,
                          PrintService printService,
                          @Value("${settings.min-correct}") Integer minCorrect) {
        this.questionDao = questionDao;
        this.respondentService = respondentService;
        this.converterService = converterService;
        this.answerService = answerService;
        this.printService = printService;
        this.minCorrect = minCorrect;
    }

    @Override
    public void runTest() {
        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        Respondent respondent = respondentService.createRespondent();

        int correctAnswers = 0;

        for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
            String text = converterService.buildQuestionText(questionWithAnswer);
            printService.print(text);

            correctAnswers = correctAnswers + answerService.getAnswer(questionWithAnswer.getAnswerVariants());
        }

        TestResult testResult = createTestResult(questionWithAnswers, correctAnswers, respondent);

        String result = converterService.buildResultText(testResult);
        printService.print(result);
    }

    private TestResult createTestResult(List<QuestionWithAnswer> questionWithAnswers,
                                        int correctAnswers, Respondent respondent) {

        return new TestResult(correctAnswers,
                questionWithAnswers.size(),
                correctAnswers >= minCorrect, respondent);
    }
}
