package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.Options;
import com.homeapp.nonsense_BE.services.OptionsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Options/")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionsController {

    private static Logger LOGGER = LogManager.getLogger(OptionsController.class);

    @Autowired
    OptionsService optionsService;

    @GetMapping("startingNewBike")
    public ResponseEntity<Options> startingNewBike() {
        LOGGER.info("Sending Options for designing new bike.");
        Options o = optionsService.startNewBike();
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}