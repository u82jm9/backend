package com.homeapp.nonsense_BE.Exceptions;

import com.homeapp.nonsense_BE.models.logger.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExceptionHandler {
    private final ErrorLogger errorLogger = new ErrorLogger();

    @Autowired
    public ExceptionHandler() {

    }

    public void handleError(String component, String method, String link) {
        errorLogger.log("An Error occurred from: " + method + "!!Connecting to link: " + link + "!!For bike Component: " + component);
    }

    public void handleIOException(String component, String method, IOException e) {
        errorLogger.log("An IOException occurred from: " + method + "!!Connecting to link: " + e.getMessage() + "!!For bike Component: " + component);
    }

    public void handleException(String component, String method, Exception e) {
        errorLogger.log("An Exception occurred from: " + method + "!!Connecting to link: " + e.getMessage() + "!!For bike Component: " + component);
    }
}
