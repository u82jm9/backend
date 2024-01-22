package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Test/")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final CustomLogger LOGGER;

    @Autowired
    public TestController(CustomLogger LOGGER) {
        this.LOGGER = LOGGER;
    }

    @GetMapping("IsThisThingOn")
    public ResponseEntity<Boolean> isThisThingOn() {
        LOGGER.log("info", "Back-end is ON!");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}