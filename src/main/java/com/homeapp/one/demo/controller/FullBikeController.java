package com.homeapp.one.demo.controller;

import com.homeapp.one.demo.models.bike.FullBike;
import com.homeapp.one.demo.services.FullBikeService;
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

    private static Logger LOGGER = LogManager.getLogger(FullBikeController.class);

    @Autowired
    private FullBikeService fullBikeService;

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

    @GetMapping("GetShimano")
    public ResponseEntity<List<String>> getShimano() {
        LOGGER.info("Get all Shimano Groupsets, API");
        List<String> shimanoNames = fullBikeService.getShimanoGroupset();
        return new ResponseEntity<List<String>>(shimanoNames, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetSram")
    public ResponseEntity<List<String>> getSram() {
        LOGGER.info("Get all Sram Groupsets, API");
        List<String> sramNames = fullBikeService.getSramGroupset();
        return new ResponseEntity<List<String>>(sramNames, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetCampag")
    public ResponseEntity<List<String>> getCampag() {
        LOGGER.info("Get all Campagnolo Groupsets, API");
        List<String> campagNames = fullBikeService.getCampagGroupset();
        return new ResponseEntity<List<String>>(campagNames, HttpStatus.ACCEPTED);
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
}