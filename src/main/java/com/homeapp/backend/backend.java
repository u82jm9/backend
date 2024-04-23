package com.homeapp.backend;

import com.homeapp.backend.services.BikePartsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homeapp.backend")
public class backend implements CommandLineRunner {
    private static BikePartsService bikePartsService = new BikePartsService();

    public static void main(String[] args) {
        bikePartsService.checkAllLinks();
        SpringApplication.run(backend.class, args);
    }

    @Override
    public void run(String... args) {

    }
}