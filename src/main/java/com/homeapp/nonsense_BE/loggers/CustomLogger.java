package com.homeapp.nonsense_BE.loggers;

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
    private TreeSet<String> logs;

    @Autowired
    public CustomLogger() {
    }

    private TreeSet<String> readLogsFile() {

        try {
            File file = new File(getFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println(e);
        }
        return new TreeSet<>();
    }

    private String getFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + ".json";
    }

    public void log(String level, String message) {
        switch (level) {
            case "info" -> info(message);
            case "error" -> error(message);
            default -> warn(message);
        }

    }

    private void info(String message) {
        message = "[INFO] - " + message;
        logToFile(message);
    }

    private void warn(String message) {
        message = "[WARN] - " + message;
        logToFile(message);
    }

    private void error(String message) {
        message = "[ERROR] - " + message;
        System.err.println(message);
        logToFile(message);
    }

    private void logToFile(String message) {
        try {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            TreeSet<String> logs = readLogsFile();
            logs.add(message);
            om.writeValue(new File(getFileName()), logs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
