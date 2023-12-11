package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Image;
import com.homeapp.nonsense_BE.services.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Image/")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    @Autowired
    ImageService imageService;

    @PostMapping("GetImages")
    public ResponseEntity<List<Image>> getOptions(@RequestBody FullBike bike) {
        LOGGER.info("Getting Images for Bike: {}", bike);
        List<Image> imageList = imageService.getImages(bike);
        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }
}