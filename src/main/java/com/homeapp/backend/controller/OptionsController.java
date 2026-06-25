package com.homeapp.backend.controller;

import com.homeapp.backend.models.bike.CombinedData;
import com.homeapp.backend.models.bike.Options;
import com.homeapp.backend.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * The type Options controller.
 * Houses multiple APIs relating to a Bike Options for FE.
 */
@RestController
@RequestMapping("Options/")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionsController {

    private final Logger logger = Logger.getLogger(OptionsController.class.getName());
    private final OptionsService optionsService;

    /**
     * Instantiates a new Options Controller.
     * Autowires in an Options Service for access to the methods.
     *
     * @param optionsService the options service
     */
    @Autowired
    public OptionsController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    /**
     * Starting a new bike, you're going to need a new Options object on the FE.
     *
     * @return the Options
     * @return HTTP - Status OK
     */
    @GetMapping("StartNewBike")
    public ResponseEntity<Options> startingNewBike() {
        logger.log(INFO, "Get Options for new bike.");
        Options o = optionsService.startNewBike();
        logger.log(WARNING, "Returning Options to FE: " + o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    /**
     * Updates Options.
     *
     * @param combinedData the combined data of a Full Bike and Options
     * @return the Options
     * @return HTTP - Status OK
     */
    @PostMapping("GetOptions")
    public ResponseEntity<Options> getOptions(@RequestBody CombinedData combinedData) {
        logger.log(INFO, "Updating Options for Bike!");
        Options o = optionsService.updateOptions(combinedData);
        logger.log(WARNING, "Returning Options to FE: " + o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}