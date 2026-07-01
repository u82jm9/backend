package com.homeapp.backend;

import com.homeapp.backend.services.StartupService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.homeapp.backend")
public class backend implements CommandLineRunner {
    static StartupService startupService = new StartupService();

    public static void main(String[] args) {
        startupService.setupProject();
        SpringApplication.run(backend.class, args);
        startupService.checkAllLinks();
    }

    @Override
    public void run(String @NonNull ... args) {

    }

}