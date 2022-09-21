package org.example.homework01;

import org.example.homework01.service.TestRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestRunner testRunner = context.getBean(TestRunner.class);
        testRunner.runTest();
    }
}
