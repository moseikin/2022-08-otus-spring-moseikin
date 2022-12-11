package org.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryMain {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryMain.class, args);
    }
}
