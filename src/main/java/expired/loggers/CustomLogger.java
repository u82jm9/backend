package expired.loggers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

@Service
public class CustomLogger {

    static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static final DateTimeFormatter LOGS_STAMP_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final ObjectMapper om = new ObjectMapper();
    private final TreeSet<String> logs;
    private final TreeSet<String> infoLogs;
    private final TreeSet<String> errorLogs;
    private final TreeSet<String> warnLogs;

    @Autowired
    public CustomLogger() {
        this.logs = readLogsFile();
        this.infoLogs = readInfoLogsFile();
        this.errorLogs = readErrorLogsFile();
        this.warnLogs = readWarnLogsFile();
    }

    private TreeSet<String> readLogsFile() {
        try {
            File file = new File(getFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                return new TreeSet<>();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e);
        }
        return new TreeSet<>();
    }

    private TreeSet<String> readInfoLogsFile() {
        try {
            File file = new File(getInfoFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                return new TreeSet<>();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e);
        }
        return new TreeSet<>();
    }

    private TreeSet<String> readErrorLogsFile() {
        try {
            File file = new File(getErrorFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                return new TreeSet<>();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e);
        }
        return new TreeSet<>();
    }

    private TreeSet<String> readWarnLogsFile() {
        try {
            File file = new File(getWarnFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                return new TreeSet<>();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e);
        }
        return new TreeSet<>();
    }

    public void log(String level, String message) {
        switch (level) {
            case "info" -> info(message);
            case "error" -> error(message);
            default -> warn(message);
        }

    }

    private void info(String message) {
        logInfoMessage(message);
        message = "[INFO] - " + message;
        logToFile(message);
    }

    private void warn(String message) {
        logWarnMessage(message);
        message = "[WARN] - " + message;
        logToFile(message);
    }

    private void error(String message) {
        logErrorMessage(message);
        message = "[ERROR] - " + message;
        logToFile(message);
    }

    private String getFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + ".json";
    }

    private String getInfoFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + "_INFO.json";
    }

    private String getErrorFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + "_ERROR.json";
    }

    private String getWarnFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + "_WARN.json";
    }

    private void logToFile(String message) {
        try {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            logs.add(message);
            om.writeValue(new File(getFileName()), logs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void logInfoMessage(String message) {
        try {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            logs.add(message);
            om.writeValue(new File(getInfoFileName()), infoLogs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void logErrorMessage(String message) {
        System.err.println("\n" + message + "\n");
        try {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            logs.add(message);
            om.writeValue(new File(getErrorFileName()), errorLogs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void logWarnMessage(String message) {
        try {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            logs.add(message);
            om.writeValue(new File(getWarnFileName()), warnLogs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
