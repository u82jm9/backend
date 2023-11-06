package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.repository.FullBikeDao;
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
    @Autowired
    private FullBikeDao fullBikeDao;

    @GetMapping("GetAll")
    public ResponseEntity<List<FullBike>> getallBikes() {
        LOGGER.info("Get all Bikes, API");
        List<FullBike> bikeList = fullBikeService.getAllFullBikes();
        return new ResponseEntity<List<FullBike>>(bikeList, HttpStatus.ACCEPTED);
    }

    @GetMapping("StartNewBike")
    public ResponseEntity<FullBike> startNewBike() {
        LOGGER.info("Starting new Bike, API");
        FullBike bike = fullBikeService.startNewBike();
        return new ResponseEntity<FullBike>(bike, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddFullBike")
    public ResponseEntity<List> addFullBike(@RequestBody FullBike bike) {
        LOGGER.info("Adding new full bike, API");
        fullBikeService.create(bike);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("UpdateBike")
    public ResponseEntity<FullBike> updateBike(@RequestBody FullBike bike) {
        FullBike updatedBike = fullBikeService.updateBike(bike);
        LOGGER.info("Updating Design Bike, API");
        return new ResponseEntity<FullBike>(updatedBike, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetGroupSets")
    public ResponseEntity<List<String>> getGroupsetBrands() {
        LOGGER.info("Get all Bikes Frames, API");
        List<String> frameNames = fullBikeService.getAllGroupsetBrandNames();
        return new ResponseEntity<List<String>>(frameNames, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetFrames")
    public ResponseEntity<List<String>> getFrameStyles() {
        LOGGER.info("Get all Bikes Frames, API");
        List<String> frameNames = fullBikeService.getAllFrameStyleNames();
        return new ResponseEntity<List<String>>(frameNames, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetBars")
    public ResponseEntity<List<String>> getBars() {
        LOGGER.info("Get all Bikes Frames, API");
        List<String> barNames = fullBikeService.getAllBars();
        return new ResponseEntity<List<String>>(barNames, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetBrakes")
    public ResponseEntity<List<String>> getBrakes() {
        LOGGER.info("Get all Bikes Frames, API");
        List<String> brakeNames = fullBikeService.getAllBrakes();
        return new ResponseEntity<List<String>>(brakeNames, HttpStatus.ACCEPTED);
    }
}