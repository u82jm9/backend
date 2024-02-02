package com.homeapp.NonsenseBE.controller;

import com.homeapp.NonsenseBE.models.bike.CombinedData;
import com.homeapp.NonsenseBE.models.bike.Options;
import com.homeapp.NonsenseBE.models.logger.InfoLogger;
import com.homeapp.NonsenseBE.models.logger.WarnLogger;
import com.homeapp.NonsenseBE.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Options controller.
 * Houses multiple APIs relating to a Bike Options for FE.
 */
@RestController
@RequestMapping("Options/")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionsController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
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
        infoLogger.log("Get Options for new bike.");
        Options o = optionsService.startNewBike();
        warnLogger.log("Returning Options to FE: " + o);
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
        infoLogger.log("Updating Options for Bike!");
        Options o = optionsService.updateOptions(combinedData);
        warnLogger.log("Returning Options to FE: " + o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}