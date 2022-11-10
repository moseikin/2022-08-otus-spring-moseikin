package org.example.testing.service;

import lombok.RequiredArgsConstructor;
import org.example.testing.domain.Respondent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondentServiceImpl implements RespondentService {

    private static final String INPUT_FIRST_NAME = "input.first.name";
    private static final String INPUT_LAST_NAME = "input.last.name";

    private final PrintService printService;
    private final ValidationService validationService;
    private final ScannerService scannerService;
    private final LocalizationMessageService localizationMessagesService;

    public Respondent createRespondent() {
        printService.print(localizationMessagesService.getMessage(INPUT_FIRST_NAME));
        String firstName = getName();

        printService.print(localizationMessagesService.getMessage(INPUT_LAST_NAME));
        String lastName = getName();

        return new Respondent(firstName, lastName);
    }

    private String getName() {
        String name = "";
        while (!validationService.isNameValid(name)) {
            name = scannerService.nextLine();
        }

        return name;
    }
}
