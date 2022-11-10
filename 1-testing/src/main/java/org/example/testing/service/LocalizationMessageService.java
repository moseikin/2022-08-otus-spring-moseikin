package org.example.testing.service;

public interface LocalizationMessageService {

    String getMessage(String messageCode);

    String getMessage(String messageCode, String... args);
}
