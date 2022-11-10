package org.example.testing.shell;

import lombok.RequiredArgsConstructor;
import org.example.testing.service.LocalizationMessageService;
import org.example.testing.service.PrintService;
import org.example.testing.service.TestRunner;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private static final String NOW_START_APP = "now.start.test";
    private static final String ASK_TO_START_BEFORE = "ask.to.start.before";
    private boolean isAskedToStart;

    private final PrintService printService;
    private final TestRunner testRunner;
    private final LocalizationMessageService localizationMessageService;

    @ShellMethod(value = "ask to start", key = {"a", "ask"})
    public void askToStart() {

        printService.print(localizationMessageService.getMessage(NOW_START_APP));
        isAskedToStart = true;
    }

    @ShellMethod(value = "run", key = {"r", "run"})
    @ShellMethodAvailability("isStartAvailable")
    public void start() {

        testRunner.runTest();
    }


    private Availability isStartAvailable() {
        return isAskedToStart ?
                Availability.available() :
                Availability.unavailable(localizationMessageService.getMessage(ASK_TO_START_BEFORE));
    }
}
