package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.logger.ErrorLoggerFE;
import com.homeapp.nonsense_BE.models.logger.InfoLoggerFE;
import com.homeapp.nonsense_BE.models.logger.WarnLoggerFE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Test/")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final InfoLoggerFE infoLogger = new InfoLoggerFE();
    private final WarnLoggerFE warnLogger = new WarnLoggerFE();
    private final ErrorLoggerFE errorLogger = new ErrorLoggerFE();

    @Autowired
    public TestController() {
    }

    @GetMapping("IsThisThingOn")
    public ResponseEntity<Boolean> isThisThingOn() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("LogThis")
    public ResponseEntity<HttpStatus> logThis(@RequestBody String log) {
        String level = log.split("!!")[0].toUpperCase();
        String message = log.split("!!")[1];
        switch (level) {
            case "WARN" -> warnLogger.log(message);
            case "INFO" -> infoLogger.log(message);
            default -> errorLogger.log(message);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}