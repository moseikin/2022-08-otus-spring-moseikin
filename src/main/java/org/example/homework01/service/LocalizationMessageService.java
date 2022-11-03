package org.example.homework01.service;

public interface LocalizationMessageService {

    String getMessage(String messageCode);

    String getMessage(String messageCode, String... args);
}
