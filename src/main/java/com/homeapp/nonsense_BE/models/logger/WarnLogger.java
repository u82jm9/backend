package com.homeapp.nonsense_BE.models.logger;

import java.time.LocalDate;

public class WarnLogger extends BaseLogger {

    @Override
    protected String getFileName() {
        return "src/main/logs/" + LocalDate.now().format(FILE_NAME_FORMATTER) + "_WARN.json";
    }
}
