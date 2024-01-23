package com.homeapp.nonsense_BE.Exceptions;

import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.Error;
import com.homeapp.nonsense_BE.models.logger.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExceptionHandler {
    private final ErrorLogger errorLogger = new ErrorLogger();
    private BikeParts bikeParts;

    @Autowired
    public ExceptionHandler(@Lazy BikeParts bikeParts) {
        this.bikeParts = bikeParts;
    }

    public void handleError(String component, String method, String link) {
        errorLogger.log("An Error occurred from: " + method + "\\nConnecting to link: " + link + "\\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, link));
        bikeParts.setErrorMessages(tempList);
    }

    public void handleIOException(String component, String method, IOException e) {
        errorLogger.log("An IOException occurred from: " + method + "\\nConnecting to link: " + e.getMessage() + "\\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
    }

    public void handleException(String component, String method, Exception e) {
        errorLogger.log("An Exception occurred from: " + method + "\\nConnecting to link: " + e.getMessage() + "\\nFor bike Component: " + component);
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
    }
}
