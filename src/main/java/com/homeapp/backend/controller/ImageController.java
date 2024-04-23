package com.homeapp.backend.controller;

import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Image;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import com.homeapp.backend.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Image controller.
 * Houses API relating to FE Images.
 */
@RestController
@RequestMapping("Image/")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ImageService imageService;

    /**
     * Instantiates a new Image controller.
     * Autowires in a Full Bike Service for access to the methods.
     *
     * @param imageService the image service
     */
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Gets list of Images for passed-in bike.
     *
     * @param bike the bike
     * @return the options
     * @return HTTP - Status OK
     */
    @PostMapping("GetImages")
    public ResponseEntity<List<Image>> getImages(@RequestBody FullBike bike) {
        infoLogger.log("Getting Images for Bike: " + bike);
        List<Image> imageList = imageService.getImages(bike);
        warnLogger.log("Returning Images to FE: " + imageList);
        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }
}