package org.example.homework01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespondentDto {
    /**
     * Respondent first name
     */
    private String firstName;

    /**
     * Respondent last name
     */
    private String lastName;

    /**
     * Correct answers count
     */
    private Integer correctAnswersCount;

    /**
     * Total questions count
     */
    private Integer questionsTotal;

    /**
     * Is test was passed
     */
    private Boolean isTestPassed;
}
