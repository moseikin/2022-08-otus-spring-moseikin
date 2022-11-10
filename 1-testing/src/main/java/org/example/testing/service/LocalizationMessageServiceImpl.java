package org.example.testing.service;

import org.example.testing.component.LocaleProvider;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LocalizationMessageServiceImpl implements LocalizationMessageService {

    private static final String DEFAULT_MESSAGE = "";

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public LocalizationMessageServiceImpl(LocaleProvider localeProvider,
                                          MessageSource messageSource) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    public String getMessage(String messageCode) {
        return messageSource.getMessage(messageCode, null, DEFAULT_MESSAGE, localeProvider.getLocale());
    }

    public String getMessage(String messageCode, String... args) {
        return messageSource.getMessage(messageCode, args, DEFAULT_MESSAGE, localeProvider.getLocale());
    }
}
