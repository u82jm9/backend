package com.homeapp.nonsense_BE.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Test/")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    private static final Logger LOGGER = LogManager.getLogger(TestController.class);

    @GetMapping("IsThisThingOn")
    public ResponseEntity<Boolean> isThisThingOn() {
        Boolean backendOn = true;
        return new ResponseEntity<>(backendOn, HttpStatus.OK);
    }
}