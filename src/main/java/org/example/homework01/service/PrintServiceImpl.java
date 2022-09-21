package org.example.homework01.service;

import org.springframework.stereotype.Service;

@Service
public class PrintServiceImpl implements PrintService {

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
