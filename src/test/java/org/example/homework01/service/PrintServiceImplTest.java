package org.example.homework01.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrintServiceImplTest {

    private final PrintServiceImpl printService = new PrintServiceImpl();

    @Test
    void print() {
        String expected = "print something";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        printService.print(expected);

        assertNotNull(outContent);

        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }
}
