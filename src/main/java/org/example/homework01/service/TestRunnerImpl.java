package org.example.homework01.service;

import lombok.RequiredArgsConstructor;
import org.example.homework01.dao.QuestionDao;
import org.example.homework01.domain.AnswerVariant;
import org.example.homework01.domain.QuestionWithAnswer;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TestRunnerImpl implements TestRunner {

    private static final String QUESTION = "The question is: ";
    private static final String CHOOSE_CORRECT_TEXT = "Choose correct: ";

    private final QuestionDao questionDao;
    private final PrintService printService;

    @Override
    public void runTest() {
        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
            String text = buildText(questionWithAnswer);
            printService.print(text);
        }
    }

    private String buildText(QuestionWithAnswer questionWithAnswer) {
        String answer = questionWithAnswer.getAnswerVariants().stream()
                .map(AnswerVariant::getText)
                .collect(Collectors.joining(", "));

        return QUESTION + questionWithAnswer.getQuestionText() + "\n" +
                CHOOSE_CORRECT_TEXT + answer + "\n";
    }
}
