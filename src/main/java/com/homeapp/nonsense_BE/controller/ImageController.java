package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Image;
import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
import com.homeapp.nonsense_BE.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Image/")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("GetImages")
    public ResponseEntity<List<Image>> getOptions(@RequestBody FullBike bike) {
        infoLogger.log("Getting Images for Bike: " + bike);
        List<Image> imageList = imageService.getImages(bike);
        warnLogger.log("Returning Images to FE: " + imageList);
        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }
}