package com.homeapp.NonsenseBE;

import com.homeapp.NonsenseBE.services.BikePartsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homeapp.NonsenseBE")
public class NonsenseBE implements CommandLineRunner {
    private static BikePartsService bikePartsService = new BikePartsService();

    public static void main(String[] args) {
        bikePartsService.checkAllLinks();
        SpringApplication.run(NonsenseBE.class, args);
    }

    @Override
    public void run(String... args) {

    }
}