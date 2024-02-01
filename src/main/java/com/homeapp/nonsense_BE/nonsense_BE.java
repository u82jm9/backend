package com.homeapp.nonsense_BE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homeapp.nonsense_BE")
public class nonsense_BE implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(nonsense_BE.class, args);
    }

    @Override
    public void run(String... args) {

    }
}