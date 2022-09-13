package org.example.homework01.service;

import org.example.homework01.dao.QuestionDao;
import org.example.homework01.domain.AnswerVariant;
import org.example.homework01.domain.QuestionWithAnswer;
import org.example.homework01.domain.Respondent;
import org.example.homework01.domain.TestResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRunnerImpl implements TestRunner {

    private static final String QUESTION_IS = "The question is: ";
    private static final String CHOOSE_CORRECT = "Choose correct: ";
    private static final String CORRECT = "Correct";
    private static final String INCORRECT = "Incorrect";
    private static final String CORRECT_ANSWERS = "Correct answers: ";
    private static final String TOTAL_QUESTIONS = "Total questions: ";
    private static final String TEST_PASSED = "Test passed";
    private static final String TEST_NOT_PASSED = "Test not passed";
    private static final String INPUT_FIRST_NAME = "Input first name: ";
    private static final String INPUT_LAST_NAME = "Input last name: ";
    private static final String YOUR_ANSWER_IS = "Your answer is from 1 to ";

    private final QuestionDao questionDao;
    private final PrintService printService;
    private final ScannerService scannerService;
    private final ValidationService validationService;

    private final Integer minCorrect;

    public TestRunnerImpl(QuestionDao questionDao, PrintService printService, ScannerService scannerService,
                          ValidationService validationService, @Value("${min-correct}") Integer minCorrect) {
        this.questionDao = questionDao;
        this.printService = printService;
        this.scannerService = scannerService;
        this.validationService = validationService;
        this.minCorrect = minCorrect;
    }

    @Override
    public void runTest() {
        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        Respondent respondent = introduceRespondent();

        int correctAnswers = 0;

        for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
            String text = buildText(questionWithAnswer);
            printService.print(text);

            correctAnswers = correctAnswers + askAQuestion(questionWithAnswer.getAnswerVariants());
        }

        TestResult testResult = new TestResult(correctAnswers,
                questionWithAnswers.size(),
                correctAnswers >= minCorrect, respondent);

        String result = makeResultText(testResult);
        printService.print(result);
    }


    private String buildText(QuestionWithAnswer questionWithAnswer) {
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

        return QUESTION_IS + questionWithAnswer.getQuestionText() + "\n" +
                CHOOSE_CORRECT + answerVariants + "\n";
    }

    private Respondent introduceRespondent() {
        printService.print(INPUT_FIRST_NAME);
        String firstName = getName();

        printService.print(INPUT_LAST_NAME);
        String lastName = getName();

        return new Respondent(firstName, lastName);
    }

    private String getName() {
        String name = "";
        while (Boolean.FALSE.equals(validationService.isNameValid(name))) {
            name = scannerService.nextLine();
        }

        return name;
    }

    private int askAQuestion(List<AnswerVariant> answerVariants) {

        Integer answerNumber = null;
        while (!validationService.isInTheBounds(answerVariants.size(), answerNumber)) {
            printService.print(YOUR_ANSWER_IS + answerVariants.size() + ":");
            answerNumber = scannerService.nextInt();

            answerNumber = reduceByOneToBeInBounds(answerNumber);
        }

        if (answerVariants.get(answerNumber).isCorrect()) {
            printService.print(CORRECT + "\n");

            return 1;
        } else {
            printService.print(INCORRECT + "\n");

            return 0;
        }
    }

    private Integer reduceByOneToBeInBounds(Integer answerNumber) {
        if (answerNumber == null) {
            return answerNumber;
        } else {
            return answerNumber - 1;
        }
    }

    private String makeResultText(TestResult testResult) {

        return testResult.getRespondent().getFirstName() + " " + testResult.getRespondent().getLastName() + "\n" +
                ((testResult.isTestPassed()) ? TEST_PASSED : TEST_NOT_PASSED) + "\n" +
                TOTAL_QUESTIONS + testResult.getQuestionsTotal() + "\n" +
                CORRECT_ANSWERS + testResult.getCorrectAnswersCount();
    }
}
