package com.homeapp.nonsense_BE.models.logger;

import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public abstract class BaseLogger {
    static final DateTimeFormatter LOGS_STAMP_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BaseLogger() {
    }

    protected abstract TreeSet<String> readLogsFile();

    protected abstract String getFileName();

    protected abstract void logToFile();


    protected abstract void log(String message);
}
