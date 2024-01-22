package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import com.homeapp.nonsense_BE.models.bike.CombinedData;
import com.homeapp.nonsense_BE.models.bike.Options;
import com.homeapp.nonsense_BE.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Options/")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionsController {

    private final CustomLogger LOGGER;
    private final OptionsService optionsService;

    @Autowired
    public OptionsController(CustomLogger LOGGER, OptionsService optionsService) {
        this.LOGGER = LOGGER;
        this.optionsService = optionsService;
    }

    @GetMapping("StartNewBike")
    public ResponseEntity<Options> startingNewBike() {
        LOGGER.log("info", "Sending Options for designing new bike.");
        Options o = optionsService.startNewBike();
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @PostMapping("GetOptions")
    public ResponseEntity<Options> getOptions(@RequestBody CombinedData combinedData) {
        LOGGER.log("info", "Updating Options for Bike!");
        Options option = optionsService.updateOptions(combinedData);
        return new ResponseEntity<>(option, HttpStatus.OK);
    }
}