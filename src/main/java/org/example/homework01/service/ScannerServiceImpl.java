package org.example.homework01.service;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerServiceImpl implements ScannerService {

    private final Scanner scanner;

    public ScannerServiceImpl(InputStream is) {
        this.scanner = new Scanner(is);
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public int nextInt() {
        int number;

        try {
            number = scanner.nextInt();

            return number;
        } catch (InputMismatchException e) {
            scanner.next();

            return -1;
        }
    }
}
