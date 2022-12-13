package com.homeapp.one.demo.controller;

import com.homeapp.one.demo.models.Enums.FrameStyle;
import com.homeapp.one.demo.models.FullBike;
import com.homeapp.one.demo.services.FullBikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("GetFrames")
    public ResponseEntity<List<String>> getFrameStyles() {
        LOGGER.info("Get all Bikes Frames, API");
        List<FrameStyle> frameList = fullBikeService.getAllFrameStyles();
        List<String> frameNames = new ArrayList<>();
        for (FrameStyle fs : frameList) {
            frameNames.add(fs.getName());
        }
        return new ResponseEntity<List<String>>(frameNames, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddFullBike")
    public ResponseEntity<List> addFullBike(@RequestBody FullBike bike) {
        LOGGER.info("Adding new full bike, API");
        fullBikeService.create(bike);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}