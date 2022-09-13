package org.example.homework01.service;

import lombok.RequiredArgsConstructor;
import org.example.homework01.dao.QuestionDao;
import org.example.homework01.domain.AnswerVariant;
import org.example.homework01.domain.Constant;
import org.example.homework01.domain.QuestionWithAnswer;
import org.example.homework01.dto.RespondentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestRunnerImpl implements TestRunner {

    private final QuestionDao questionDao;
    private final PrintService printService;
    private final ScannerService scannerService;
    private final Constant constant;


    @Override
    public void runTest() {
        List<QuestionWithAnswer> questionWithAnswers = questionDao.getQuestionsAndAnswers();

        RespondentDto respondentDto = introduceRespondent();

        int correctAnswers = 0;

        for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
            String text = buildText(questionWithAnswer);
            printService.print(text);
            // TODO 1... 2... 3...
            correctAnswers = correctAnswers + askAQuestion(questionWithAnswer.getAnswerVariants());
        }

        respondentDto.setCorrectAnswersCount(correctAnswers);
        respondentDto.setQuestionsTotal(questionWithAnswers.size());
        respondentDto.setIsTestPassed(correctAnswers >= constant.getMinCorrect());

        String result = makeResultText(respondentDto);
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

        return constant.getQuestion() + questionWithAnswer.getQuestionText() + "\n" +
                constant.getChooseCorrect() + answerVariants + "\n";
    }

    private RespondentDto introduceRespondent() {
        RespondentDto respondentDto = new RespondentDto();
        String firstName = scannerService.nextLine();

        String lastName = scannerService.nextLine();

        respondentDto.setFirstName(firstName);
        respondentDto.setLastName(lastName);

        return respondentDto;
    }

    private int askAQuestion(List<AnswerVariant> answerVariants) {
        int answerNumber = scannerService.nextInt();

        if (Boolean.TRUE.equals(answerVariants.get(answerNumber - 1).getIsCorrect())) {
            return 1;
        } else {
            return 0;
        }
    }

    private String makeResultText(RespondentDto respondentDto) {

        return respondentDto.getFirstName() + " " + respondentDto.getLastName() + "\n" +
                (Boolean.TRUE.equals(respondentDto.getIsTestPassed()) ? constant.getTestIsPassed() : constant.getTestNotPassed()) + "\n" +
                constant.getTotalQuestions() + respondentDto.getQuestionsTotal() + "\n" +
                constant.getCorrectAnswersNumber() + respondentDto.getCorrectAnswersCount();
    }
}
