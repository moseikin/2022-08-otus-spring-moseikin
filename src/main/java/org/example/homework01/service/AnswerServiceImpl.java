package org.example.homework01.service;

import lombok.RequiredArgsConstructor;
import org.example.homework01.domain.AnswerVariant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private static final String CODE_CORRECT = "correct";
    private static final String CODE_INCORRECT = "incorrect";
    private static final String CODE_YOUR_ANSWER_IS = "your.answer.is";

    private final ScannerService scannerService;
    private final PrintService printService;
    private final ValidationService validationService;
    private final LocalizationMessageService localizationMessageService;

    @Override
    public int getAnswer(List<AnswerVariant> answerVariants) {
        int answerNumber = -1;

        while (!validationService.isInTheBounds(answerVariants.size(), answerNumber)) {
            String yourAnswerMessage =
                    localizationMessageService.getMessage(CODE_YOUR_ANSWER_IS, String.valueOf(answerVariants.size()));

            printService.print(yourAnswerMessage);

            answerNumber = scannerService.nextInt();

            answerNumber = reduceByOneToBeInBounds(answerNumber);
        }

        if (answerVariants.get(answerNumber).isCorrect()) {
            printService.println(localizationMessageService.getMessage(CODE_CORRECT));

            return 1;
        } else {
            printService.println(localizationMessageService.getMessage(CODE_INCORRECT));

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
}
