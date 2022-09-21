package org.example.homework01.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestResult {

    private final int correctAnswersCount;

    private final int questionsTotal;

    private final boolean isTestPassed;

    private final Respondent respondent;
}
