package org.example.homework01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScannerServiceImplTest {

    @InjectMocks
    private ScannerServiceImpl scannerService;

    @Test
    void nextLine() {
        String expected = "string";

        InputStream is = new ByteArrayInputStream(expected.getBytes());
        System.setIn(is);

        String actual = scannerService.nextLine();

        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void nextInt() {
        Integer expected = 5;

        InputStream is = new ByteArrayInputStream(expected.toString().getBytes());
        System.setIn(is);

        Integer actual = scannerService.nextInt();

        assertEquals(expected, actual);
    }

    @Test
    void nextInt_Should_ReturnNull() {
        String expected = "not-digit";

        InputStream is = new ByteArrayInputStream(expected.getBytes());
        System.setIn(is);

        Integer actual = scannerService.nextInt();

        assertNull(actual);
    }

    @Test
    void nextInt_Should_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> scannerService.nextInt());
    }
}
