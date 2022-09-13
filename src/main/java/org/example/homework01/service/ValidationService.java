package org.example.homework01.service;

public interface ValidationService {

    boolean isNameValid(String name);

    boolean isInTheBounds(int size, Integer value);
}
