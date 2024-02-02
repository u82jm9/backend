package com.homeapp.NonsenseBE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homeapp.NonsenseBE")
public class NonsenseBE implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NonsenseBE.class, args);
    }

    @Override
    public void run(String... args) {

    }
}