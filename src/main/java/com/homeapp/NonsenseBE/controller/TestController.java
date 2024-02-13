package com.homeapp.NonsenseBE.controller;

import com.homeapp.NonsenseBE.models.bike.Log;
import com.homeapp.NonsenseBE.models.logger.ErrorLoggerFE;
import com.homeapp.NonsenseBE.models.logger.InfoLoggerFE;
import com.homeapp.NonsenseBE.models.logger.WarnLoggerFE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The Test controller.
 * Houses APIs not specifically relating to BE objects, just for general BE usage.
 */
@RestController
@RequestMapping("Test/")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
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
     * Log this!
     * Separates out different log levels before calling the appropriate logger for the message passed-in.
     *
     * @param log the log message
     * @return HTTP - Status CREATED
     */
    @PutMapping("LogThis")
    public ResponseEntity<HttpStatus> logThis(@RequestBody Log log) {
        String level = log.getMessage().split("!!")[0].toUpperCase();
        String message = log.getMessage().split("!!")[1];
        switch (level) {
            case "WARN" -> warnLogger.log(message);
            case "INFO" -> infoLogger.log(message);
            default -> errorLogger.log(message);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}