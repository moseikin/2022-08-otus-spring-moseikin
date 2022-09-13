package org.example.homework01.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

// TODO привести в порядок
@Service
public class ScannerServiceImpl implements ScannerService {

    private final Scanner scanner = new Scanner(System.in);

    public String nextLine() {
        while (true) {
            String line = scanner.nextLine();
            return line;
        }
    }

    public Integer nextInt() {
        while (true) {
            int number = scanner.nextInt();
            return number;
        }
    }
}
