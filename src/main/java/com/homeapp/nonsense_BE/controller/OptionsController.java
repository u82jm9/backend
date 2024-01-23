package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.CombinedData;
import com.homeapp.nonsense_BE.models.bike.Options;
import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
import com.homeapp.nonsense_BE.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Options/")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionsController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final OptionsService optionsService;

    @Autowired
    public OptionsController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    @GetMapping("StartNewBike")
    public ResponseEntity<Options> startingNewBike() {
        infoLogger.log("Get Options for new bike.");
        Options o = optionsService.startNewBike();
        warnLogger.log("Returning Options to FE: " + o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @PostMapping("GetOptions")
    public ResponseEntity<Options> getOptions(@RequestBody CombinedData combinedData) {
        infoLogger.log("Updating Options for Bike!");
        Options o = optionsService.updateOptions(combinedData);
        warnLogger.log("Returning Options to FE: " + o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}