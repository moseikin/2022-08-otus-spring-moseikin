package org.example.homework01;

import org.example.homework01.service.TestRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        TestRunner testRunner = context.getBean(TestRunner.class);
        testRunner.runTest();
    }
}
