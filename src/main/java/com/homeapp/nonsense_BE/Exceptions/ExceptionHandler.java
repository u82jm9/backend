package com.homeapp.nonsense_BE.Exceptions;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExceptionHandler {
    private final CustomLogger LOGGER;
    private BikeParts bikeParts;

    @Autowired
    public ExceptionHandler(@Lazy CustomLogger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public void handleError(String component, String method, String link) {
        LOGGER.log("error", "An Error occurred from: " + method + "\nConnecting to link: " + link + "\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, link));
        bikeParts.setErrorMessages(tempList);
    }

    public void handleIOException(String component, String method, IOException e) {
        LOGGER.log("error", "An IOException occurred from: " + method + "\nConnecting to link: " + e.getMessage() + "\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
    }

    public void handleException(String component, String method, Exception e) {
        LOGGER.log("error", "An Exception occurred from: " + method + "\nConnecting to link: " + e.getMessage() + "\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
    }
}
