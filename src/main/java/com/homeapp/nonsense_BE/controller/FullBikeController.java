package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FullBike/")
@CrossOrigin(origins = "http://localhost:3000")
public class FullBikeController {

    private static final Logger LOGGER = LogManager.getLogger(FullBikeController.class);

    @Autowired
    private FullBikeService fullBikeService;

    @GetMapping("GetAll")
    public ResponseEntity<List<FullBike>> getallBikes() {
        LOGGER.info("Get all Bikes, API");
        List<FullBike> bikeList = fullBikeService.getAllFullBikes();
        return new ResponseEntity<>(bikeList, HttpStatus.ACCEPTED);
    }

    @GetMapping("StartNewBike")
    public ResponseEntity<FullBike> startNewBike() {
        LOGGER.info("Starting new Bike, API");
        FullBike bike = fullBikeService.startNewBike();
        return new ResponseEntity<>(bike, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddFullBike")
    public ResponseEntity<HttpStatus> addFullBike(@RequestBody FullBike bike) {
        LOGGER.info("Adding new full bike, API");
        fullBikeService.create(bike);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("UpdateBike")
    public ResponseEntity<FullBike> updateBike(@RequestBody FullBike bike) {
        FullBike updatedBike = fullBikeService.updateBike(bike);
        LOGGER.info("Updating Design Bike, API");
        return new ResponseEntity<>(updatedBike, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("DeleteBike")
    public ResponseEntity<HttpStatus> deleteBike(@RequestBody FullBike bike) {
        fullBikeService.deleteBike(bike.getFullBikeId());
        LOGGER.info("Deleting Bike from DB with id {}", bike.getFullBikeId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}