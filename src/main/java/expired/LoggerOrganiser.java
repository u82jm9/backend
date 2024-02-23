package expired;

import com.homeapp.NonsenseBE.models.logger.ErrorLogger;
import com.homeapp.NonsenseBE.models.logger.InfoLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * The Class Logger organiser, used to move old logs files to expired directory.
 */
public class LoggerOrganiser {
    static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    static final String today = LocalDate.now().format(FILE_NAME_FORMATTER);
    private final String LOGS_DIRECTORY_PATH = "src/main/logs/";
    private final String EXPIRED_DIRECTORY_PATH = "src/main/logs/expired";
    private final ErrorLogger errorLogger = new ErrorLogger();
    private final InfoLogger infoLogger = new InfoLogger();

    /**
     * Instantiates a new Logger organiser.
     */
    public LoggerOrganiser() {
    }

    /**
     * Moves old log files to expired directory.
     * Meaning that that the top level of logs directory should only contain the most recent set of log files.
     */
    public void moveOldLogFiles() {
        File logsDir = new File(LOGS_DIRECTORY_PATH);
        File expiredLogsDir = new File(EXPIRED_DIRECTORY_PATH);

        File[] logFiles = logsDir.listFiles();
        if (logFiles != null) {
            Arrays.stream(logFiles).filter(f -> !f.getName().contains(today))
                    .forEach(f -> {
                        Path source = Paths.get(f.getAbsolutePath());
                        Path target = Paths.get(expiredLogsDir.getAbsolutePath(), f.getName());
                        try {
                            Files.move(source, target);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
