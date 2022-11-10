package org.example.testing.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
public class LocaleProvider {

    private static final String DEFAULT_LANGUAGE = "ru_RU";

    private final Locale locale;

    public LocaleProvider(@Value("${settings.language:" + DEFAULT_LANGUAGE + "}") String language) {
        this.locale = new Locale(language);
    }
}
