package org.example.testing.service;

public interface ValidationService {

    boolean isNameValid(String name);

    boolean isInTheBounds(int size, int value);
}
