package org.example.homework01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ValidationServiceImplTest {

    @Autowired
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
        assertFalse(validationServiceImpl.isInTheBounds(3, 3));

        assertFalse(validationServiceImpl.isInTheBounds(3, -3));
    }
}
