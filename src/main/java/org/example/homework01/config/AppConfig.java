package org.example.homework01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/settings.properties")
public class AppConfig {
}
