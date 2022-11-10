package org.example.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;

@Service
public class PrintServiceImpl implements PrintService {

    private final PrintStream out;

    public PrintServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(String text) {
        out.println(text);
    }

    @Override
    public void printList(List<String> text) {
        text.forEach(out::println);
    }
}
