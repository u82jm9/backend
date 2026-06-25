package expired.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * The Exception Handler Service Class. Used to take Exceptions generated and pass them into the errorLogger more neatly.
 * Creates much more flexibility for methods that do throw an exception.
 */
@Service
public class ExceptionHandler {
    private Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    /**
     * Instantiates a new Exception handler.
     */
    @Autowired
    public ExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    /**
     * Handle error.
     *
     * @param component the component
     * @param method    the method
     * @param link      the link
     */
    public void handleError(String component, String method, String link) {
        logger.log(SEVERE, "An Error occurred from: " + method + "!!" + "\nConnecting to link: " + link + "!!" + "\nFor bike Component: " + component);
    }

    /**
     * Handle io exception.
     *
     * @param component the component
     * @param method    the method
     * @param e         the e
     */
    public void handleIOException(String component, String method, IOException e) {
        logger.log(SEVERE, "An IOException occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
    }

    /**
     * Handle exception.
     *
     * @param component the component
     * @param method    the method
     * @param e         the e
     */
    public void handleException(String component, String method, Exception e) {
        logger.log(SEVERE, "An Exception occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
    }
}
