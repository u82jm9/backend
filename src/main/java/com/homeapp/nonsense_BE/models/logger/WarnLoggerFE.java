package com.homeapp.nonsense_BE.models.logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class WarnLoggerFE extends BaseLogger {
    private final ObjectMapper om = new ObjectMapper();
    private final TreeSet<String> logs;

    public WarnLoggerFE() {
        this.logs = readLogsFile();
    }

    @Override
    protected TreeSet<String> readLogsFile() {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + "_Front-End-WARN.json";
    }

    @Override
    protected void logToFile() {
        try {
            om.writeValue(new File(getFileName()), logs);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void log(String message) {
        synchronized (this) {
            message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
            List<String> m = Arrays.stream(message.split("!!")).toList();
            logs.addAll(m);
            logToFile();
        }
    }
}
