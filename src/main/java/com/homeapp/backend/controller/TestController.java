package com.homeapp.backend.controller;

import com.homeapp.backend.models.DTOJoke;
import com.homeapp.backend.models.DTOLog;
import com.homeapp.backend.models.FuelPrice;
import com.homeapp.backend.models.logger.ErrorLoggerFE;
import com.homeapp.backend.models.logger.InfoLoggerFE;
import com.homeapp.backend.models.logger.WarnLoggerFE;
import com.homeapp.backend.services.AdventureService;
import com.homeapp.backend.services.FuelPriceService;
import com.homeapp.backend.services.SaveJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * The Test controller.
 * Houses APIs not specifically relating to BE objects, just for general BE usage.
 */
@RestController
@RequestMapping("Test/")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final AdventureService adventureService = new AdventureService();
    private final SaveJokeService saveJokeService = new SaveJokeService();
    private final FuelPriceService fuelPriceService = new FuelPriceService();
    private final InfoLoggerFE infoLogger = new InfoLoggerFE();
    private final WarnLoggerFE warnLogger = new WarnLoggerFE();
    private final ErrorLoggerFE errorLogger = new ErrorLoggerFE();

    /**
     * Instantiates a new Test controller.
     */
    @Autowired
    public TestController() {
    }

    /**
     * Is this thing on.
     * Used by FE to check if BE is up and running. FE pings this API every few seconds.
     *
     * @return HTTP - Status OK
     */
    @GetMapping("IsThisThingOn")
    public ResponseEntity<Boolean> isThisThingOn() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * DTOLog this!
     * Separates out different DTOLog levels before calling the appropriate logger for the message passed-in.
     *
     * @param dtoLog the DTOLog message
     * @return HTTP - Status CREATED
     */
    @PostMapping("LogThis")
    public ResponseEntity<HttpStatus> logThis(@RequestBody DTOLog dtoLog) {
        switch (dtoLog.getLevel()) {
            case "WARN" -> warnLogger.log(dtoLog.getMessage());
            case "INFO" -> infoLogger.log(dtoLog.getMessage());
            default -> errorLogger.log(dtoLog.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("SaveThis")
    public ResponseEntity<HttpStatus> saveThis(@RequestBody DTOJoke joke) {
        saveJokeService.save(joke);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("GetSavedJokes")
    public ResponseEntity<ArrayList<DTOJoke>> getSavedJokes() {
        ArrayList<DTOJoke> allJokes = saveJokeService.readSavedJokesFile();
        return new ResponseEntity<>(allJokes, HttpStatus.ACCEPTED);
    }

    @GetMapping("GetFuelPrices")
    public ResponseEntity<List<FuelPrice>> getFuelPrices() {
        List<FuelPrice> prices = fuelPriceService.retriveJSONFile();
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @PostMapping(value = "UploadFile", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("tileName") String tileName) {
        adventureService.uploadFile(file, tileName);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("GetFilesFromDirectory/{directory}/{tile}")
    public ResponseEntity<List<String>> getFilesFromDirectory(@PathVariable String directory, @PathVariable String tile) {
        String directoryTile = directory + "/" + tile;
        ArrayList<String> resources = new ArrayList<>();
        adventureService.getFiles(directoryTile).forEach(r -> resources.add(r.getFilename()));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("GetSpecificFile/{directory}/{tile}/{fileName}")
    public Resource getSpecificFile(@PathVariable String directory, @PathVariable String tile, @PathVariable String fileName) {
        String filePath = directory + "/" + tile + "/" + fileName;
        Resource resource = adventureService.getSpecificFile(filePath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource).getBody();
    }
}