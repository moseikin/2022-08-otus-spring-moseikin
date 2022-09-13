package org.example.homework01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {

    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void isNameValid() {
        assertTrue(validationServiceImpl.isNameValid("name"));

        assertTrue(validationServiceImpl.isNameValid("NAME"));
    }

    @Test
    void isNameValid_Should_ReturnFalse() {
        assertFalse(validationServiceImpl.isNameValid("234gg34yug4"));

        assertFalse(validationServiceImpl.isNameValid(""));

        assertFalse(validationServiceImpl.isNameValid(null));
    }

    @Test
    void isInTheBounds() {
        assertTrue(validationServiceImpl.isInTheBounds(3, 2));
    }

    @Test
    void isInTheBounds_Should_ReturnFalse() {
        assertFalse(validationServiceImpl.isInTheBounds(3, null));

        assertFalse(validationServiceImpl.isInTheBounds(3, 3));

        assertFalse(validationServiceImpl.isInTheBounds(3, -3));
    }
}
