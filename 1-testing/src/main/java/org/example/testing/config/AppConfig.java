package org.example.testing.config;

import org.example.testing.service.ScannerService;
import org.example.testing.service.ScannerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ScannerService scannerService() {
        return new ScannerServiceImpl(System.in);
    }
}
