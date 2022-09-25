package org.example.homework01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ScannerServiceImplTest {

    private ScannerServiceImpl scannerService;

    @Test
    void nextLine() {
        String expected = "string";

        InputStream is = new ByteArrayInputStream(expected.getBytes());

        scannerService = new ScannerServiceImpl(is);

        String actual = scannerService.nextLine();

        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void nextInt() {
        Integer expected = 5;

        InputStream is = new ByteArrayInputStream(expected.toString().getBytes());

        scannerService = new ScannerServiceImpl(is);

        Integer actual = scannerService.nextInt();

        assertEquals(expected, actual);
    }

    @Test
    void nextInt_Should_ReturnNull() {
        int expected = -1;

        InputStream is = new ByteArrayInputStream("not-digit".getBytes());

        scannerService = new ScannerServiceImpl(is);

        int actual = scannerService.nextInt();

        assertEquals(expected, actual);
    }
}
