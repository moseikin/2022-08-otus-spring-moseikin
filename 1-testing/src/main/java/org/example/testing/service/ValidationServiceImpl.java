package org.example.testing.service;

import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private static final String ANY_DIGIT_REGEX = ".*\\d.*";

    public boolean isNameValid(String name) {
        return name != null && !name.matches(ANY_DIGIT_REGEX) && !name.isEmpty();
    }

    public boolean isInTheBounds(int size, int value) {

        return value >= 0 && value < size;
    }
}
