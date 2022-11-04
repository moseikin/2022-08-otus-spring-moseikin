package org.example.homework01.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PrintServiceImplTest {

    private ByteArrayOutputStream outContent;

    private PrintServiceImpl printService;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();

        printService = new PrintServiceImpl(new PrintStream(outContent));
    }

    @Test
    void print() {
        String expected = "print something";

        assertNotNull(outContent);

        printService.print(expected);

        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    void println() {
        String text = "print something else";

        printService.println(text);

        String expected = text + System.lineSeparator() + System.lineSeparator();

        assertNotNull(outContent);

        assertEquals(expected, outContent.toString());
    }
}
