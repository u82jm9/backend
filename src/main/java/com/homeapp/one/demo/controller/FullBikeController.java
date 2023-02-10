package com.homeapp.one.demo.controller;

import com.homeapp.one.demo.models.bike.Enums.FrameStyle;
import com.homeapp.one.demo.models.bike.Frame;
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
        Frame frame = new Frame();
        frame.setFrameStyle(FrameStyle.NONE_SELECTED);
        FullBike bike = new FullBike();
        bike.setBikeName("Your Custom Bike");
        bike.setFrame(frame);
        return new ResponseEntity<FullBike>(bike, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetFrames")
    public ResponseEntity<List<String>> getFrameStyles() {
        LOGGER.info("Get all Bikes Frames, API");
        List<String> frameNames = fullBikeService.getAllFrameStyleNames();
        return new ResponseEntity<List<String>>(frameNames, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddFullBike")
    public ResponseEntity<List> addFullBike(@RequestBody FullBike bike) {
        LOGGER.info("Adding new full bike, API");
        fullBikeService.create(bike);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("UpdateBike")
    public ResponseEntity<FullBike> updateBike(@RequestBody FullBike bike) {
        LOGGER.info("Updating Design Bike, API");
        return new ResponseEntity<FullBike>(bike, HttpStatus.ACCEPTED);
    }
}