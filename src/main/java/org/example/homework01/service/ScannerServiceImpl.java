package org.example.homework01.service;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class ScannerServiceImpl implements ScannerService {

    public String nextLine() {
        return new Scanner(System.in).nextLine();
    }

    public Integer nextInt() {
        int number;

        try {
            number = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            return null;
        }

        return number;
    }
}
