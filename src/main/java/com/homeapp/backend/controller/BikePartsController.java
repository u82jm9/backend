package com.homeapp.backend.controller;

import com.homeapp.backend.models.bike.BikeParts;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.services.BikePartsService;
import com.homeapp.backend.services.FullBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 * The Bike Parts Controller. Houses API for Bike Parts.
 * Used to return Bike Parts for design Bike. Complete with part name, price and link.
 * The parts have the prices summed into a total for display on FE.
 */
@RestController
@RequestMapping("Parts/")
@CrossOrigin(origins = "http://localhost:3000")
public class BikePartsController {

    private final Logger logger = Logger.getLogger(BikePartsController.class.getName());
    private final BikePartsService bikePartsService;
    private final FullBikeService fullBikeService;

    /**
     * Instantiates a new Bike Parts Controller.
     * Autowires in Full Bike and Bike Parts Services to allow access to their methods.
     *
     * @param bikePartsService the bike parts service
     * @param fullBikeService  the full bike service
     */
    @Autowired
    public BikePartsController(BikePartsService bikePartsService, FullBikeService fullBikeService) {
        this.bikePartsService = bikePartsService;
        this.fullBikeService = fullBikeService;
    }

    /**
     * Gets all parts for passed-in bike.
     *
     * @param bike the design bike
     * @return the Bike Parts
     * @return HTTP status - ACCEPTED if no errors were encountered from Bike links
     * @return HTTP status - OK if errors were encountered from Bike links
     */
    @PostMapping("GetAllParts")
    public ResponseEntity<BikeParts> getAllParts(@RequestBody FullBike bike) {
        logger.log(INFO, "Get Bike Parts, API");
        fullBikeService.setBike(bike);
        BikeParts bikeParts = bikePartsService.getBikePartsForBike();
        if (bikeParts.getErrorMessages().isEmpty()) {
            logger.log(WARNING, "Returning Parts with ZERO errors!");
            return new ResponseEntity<>(bikeParts, HttpStatus.ACCEPTED);
        } else {
            logger.log(SEVERE, "Returning Parts for bike: " + bike.getBikeName() + "; with some errors: " + bikeParts.getErrorMessages());
            return new ResponseEntity<>(bikeParts, HttpStatus.OK);
        }
    }
}