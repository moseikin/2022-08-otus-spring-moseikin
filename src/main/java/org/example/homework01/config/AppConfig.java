package org.example.homework01.config;

import org.example.homework01.service.ScannerService;
import org.example.homework01.service.ScannerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ScannerService scannerService() {
        return new ScannerServiceImpl(System.in);
    }
}
