package org.example.homework01.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PrintServiceImpl implements PrintService {

    private final String lineSeparator = System.lineSeparator();

    private final PrintStream out;

    public PrintServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(String text) {
        out.println(text);
    }

    @Override
    public void println(String text) {
        out.println(text + lineSeparator);
    }
}
