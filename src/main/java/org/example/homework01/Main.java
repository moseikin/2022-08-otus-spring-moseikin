package org.example.homework01;

import org.example.homework01.service.TestRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        TestRunner testRunner = context.getBean(TestRunner.class);
        testRunner.runTest();
    }
}
