package org.example.homework01.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Respondent {

    /**
     * Respondent first name
     */
    private final String firstName;

    /**
     * Respondent last name
     */
    private final String lastName;

    /**
     * Correct answers count
     */
    private final Integer correctAnswersCount;

    /**
     * Total questions count
     */
    private final Integer questionsTotal;

    /**
     * Is test was passed
     */
    private final Boolean isTestPassed;
}
