package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.services.BikePartsService;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FullBike/")
@CrossOrigin(origins = "http://localhost:3000")
public class BikePartsController {

    private final CustomLogger LOGGER;
    private final BikePartsService bikePartsService;
    private final FullBikeService fullBikeService;

    @Autowired
    public BikePartsController(CustomLogger LOGGER, @Lazy BikePartsService bikePartsService, FullBikeService fullBikeService) {
        this.LOGGER = LOGGER;
        this.bikePartsService = bikePartsService;
        this.fullBikeService = fullBikeService;
    }

    @PostMapping("GetAllParts")
    public ResponseEntity<BikeParts> getAllParts(@RequestBody FullBike bike) {
        LOGGER.log("info", "Get Bike Parts, API");
        fullBikeService.setBike(bike);
        BikeParts bikeParts = bikePartsService.getBikePartsForBike();
        if (bikeParts.getErrorMessages().size() == 0) {
            return new ResponseEntity<>(bikeParts, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(bikeParts, HttpStatus.OK);
        }
    }
}