package org.example.homework01.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Constant {

    private final String question;
    private final String chooseCorrect;
    private final String respondent;
    private final String correct;
    private final String incorrect;
    private final String correctAnswersNumber;
    private final String totalQuestions;
    private final String testIsPassed;
    private final String testNotPassed;
    private final Integer minCorrect;

    public Constant(@Value("${question}") String question, @Value("${choose-correct}") String chooseCorrect,
                    @Value("${respondent}") String respondent, @Value("${correct}") String correct,
                    @Value("${incorrect}") String incorrect, @Value("${correct-answers-number}") String correctAnswersNumber,
                    @Value("${total-questions}") String totalQuestions, @Value("${test-is-passed}") String testIsPassed,
                    @Value("${test-not-passed}") String testNotPassed, @Value("${min-correct}") Integer minCorrect) {
        this.question = question;
        this.chooseCorrect = chooseCorrect;
        this.respondent = respondent;
        this.correct = correct;
        this.incorrect = incorrect;
        this.correctAnswersNumber = correctAnswersNumber;
        this.totalQuestions = totalQuestions;
        this.testIsPassed = testIsPassed;
        this.testNotPassed = testNotPassed;
        this.minCorrect = minCorrect;
    }
}
