package com.homeapp.NonsenseBE.models.bike;

import javax.persistence.*;

/**
 * The DTOLog object. Used as a Data Transfer Object by the Logger system to record issues from the FE.
 */
@Entity
public class DTOLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logGen")
    @SequenceGenerator(name = "logGen", sequenceName = "LOG_SEQ", allocationSize = 1)
    private long logId;

    private String level;
    private String message;

    /**
     * Zero argument Constructor to Instantiate a new DTOLog.
     */
    public DTOLog() {
    }

    /**
     * Instantiates a new DTOLog.
     *
     * @param message the message
     */
    public DTOLog(String level, String message) {
        this.level = level;
        this.message = message;
    }

    /**
     * Gets log id.
     *
     * @return the image id
     */
    public long getLogId() {
        return logId;
    }

    /**
     * Gets Log level.
     *
     * @return the Log level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets Log level.
     *
     * @param level the level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Gets Log message.
     *
     * @return the Log message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets Log message.
     *
     * @param message the Log message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DTOLog{" +
                ", logID='" + logId + '\'' +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}