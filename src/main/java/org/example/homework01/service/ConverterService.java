package org.example.homework01.service;

import lombok.RequiredArgsConstructor;
import org.example.homework01.domain.AnswerVariant;
import org.example.homework01.domain.QuestionWithAnswer;
import org.example.homework01.domain.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private static final String QUESTION_IS = "question.is";
    private static final String CHOOSE_CORRECT = "choose.correct";
    private static final String CORRECT_ANSWERS = "correct.answers";
    private static final String TOTAL_QUESTIONS = "total.questions";
    private static final String TEST_PASSED = "test.passed";
    private static final String TEST_NOT_PASSED = "test.failed";
    private final String lineSeparator = System.lineSeparator();

    private final LocalizationMessageService localizationMessagesService;

    public String buildQuestionText(QuestionWithAnswer questionWithAnswer) {
        List<AnswerVariant> answerVariantsList = questionWithAnswer.getAnswerVariants();

        StringBuilder answerVariants = new StringBuilder();

        for (int i = 0; i < answerVariantsList.size(); i++) {
            answerVariants.append(i + 1).append(". ").append(answerVariantsList.get(i).getText());
            if (i == (answerVariantsList.size() - 1)) {
                answerVariants.append(".");
            } else {
                answerVariants.append(", ");
            }
        }

        return localizationMessagesService.getMessage(QUESTION_IS) +
                questionWithAnswer.getQuestionText() + System.lineSeparator() +
                localizationMessagesService.getMessage(CHOOSE_CORRECT) + answerVariants + lineSeparator;
    }

    public String buildResultText(TestResult testResult) {
        String testPassed = ((testResult.isTestPassed()) ? localizationMessagesService.getMessage(TEST_PASSED) :
                localizationMessagesService.getMessage(TEST_NOT_PASSED));
        String totalQuestions =
                localizationMessagesService.getMessage(TOTAL_QUESTIONS) + testResult.getQuestionsTotal();

        return testResult.getRespondent().getFirstName() + " " +
                testResult.getRespondent().getLastName() + lineSeparator +
                testPassed + lineSeparator +
                totalQuestions + lineSeparator +
                localizationMessagesService.getMessage(CORRECT_ANSWERS) +
                testResult.getCorrectAnswersCount();
    }
}
