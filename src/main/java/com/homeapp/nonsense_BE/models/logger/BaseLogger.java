package com.homeapp.nonsense_BE.models.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseLogger {
    static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter LOGS_STAMP_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final Set<String> logsToWrite;

    public BaseLogger() {
        this.logsToWrite = new TreeSet<>();
    }

    protected abstract String getFileName();

    private void logToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFileName(), true));
            for (String log : logsToWrite) {
                bw.write(log);
                bw.newLine();
            }
            logsToWrite.clear();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public synchronized void log(String message) {
        message = "[" + LocalDateTime.now().format(LOGS_STAMP_FORMATTER) + "] - " + message;
        logsToWrite.add(message);
        logToFile();
    }

}
