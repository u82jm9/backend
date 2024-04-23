package expired;

import com.homeapp.backend.models.logger.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The Exception Handler Service Class. Used to take Exceptions generated and pass them into the errorLogger more neatly.
 * Creates much more flexibility for methods that do throw an exception.
 */
@Service
public class ExceptionHandler {
    private ErrorLogger errorLogger = new ErrorLogger();

    /**
     * Instantiates a new Exception handler.
     */
    @Autowired
    public ExceptionHandler(ErrorLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    /**
     * Handle error.
     *
     * @param component the component
     * @param method    the method
     * @param link      the link
     */
    public void handleError(String component, String method, String link) {
        errorLogger.log("An Error occurred from: " + method + "!!Connecting to link: " + link + "!!For bike Component: " + component);
    }

    /**
     * Handle io exception.
     *
     * @param component the component
     * @param method    the method
     * @param e         the e
     */
    public void handleIOException(String component, String method, IOException e) {
        errorLogger.log("An IOException occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
    }

    /**
     * Handle exception.
     *
     * @param component the component
     * @param method    the method
     * @param e         the e
     */
    public void handleException(String component, String method, Exception e) {
        errorLogger.log("An Exception occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
    }
}
