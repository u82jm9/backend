package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FullBike/")
@CrossOrigin(origins = "http://localhost:3000")
public class FullBikeController {

    private final CustomLogger LOGGER;
    private final FullBikeService fullBikeService;

    @Autowired
    public FullBikeController(CustomLogger LOGGER, FullBikeService fullBikeService) {
        this.LOGGER = LOGGER;
        this.fullBikeService = fullBikeService;
    }

    @GetMapping("GetAll")
    public ResponseEntity<List<FullBike>> getallBikes() {
        LOGGER.log("info", "Get all Bikes, API");
        List<FullBike> bikeList = fullBikeService.getAllFullBikes();
        return new ResponseEntity<>(bikeList, HttpStatus.ACCEPTED);
    }

    @GetMapping("StartNewBike")
    public ResponseEntity<FullBike> startNewBike() {
        LOGGER.log("info", "Starting new Bike, API");
        FullBike bike = fullBikeService.startNewBike();
        return new ResponseEntity<>(bike, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddFullBike")
    public ResponseEntity<HttpStatus> addFullBike(@RequestBody FullBike bike) {
        LOGGER.log("info", "Adding new full bike, API");
        fullBikeService.create(bike);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("UpdateBike")
    public ResponseEntity<FullBike> updateBike(@RequestBody FullBike bike) {
        FullBike updatedBike = fullBikeService.updateBike(bike);
        LOGGER.log("info", "Updating Design Bike, API");
        return new ResponseEntity<>(updatedBike, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("DeleteBike")
    public ResponseEntity<HttpStatus> deleteBike(@RequestBody FullBike bike) {
        fullBikeService.deleteBike(bike.getFullBikeId());
        LOGGER.log("info", "Deleting Bike from DB with id " + bike.getFullBikeId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}