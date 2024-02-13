package com.homeapp.NonsenseBE.models.bike;

import javax.persistence.*;

/**
 * The Log object. Used by the Logger system to record issues from the FE.
 */
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logGen")
    @SequenceGenerator(name = "logGen", sequenceName = "LOG_SEQ", allocationSize = 1)
    private long logId;

    private String message;

    /**
     * Zero argument Constructor to Instantiate a new Log.
     */
    public Log() {
    }

    /**
     * Instantiates a new Log.
     *
     * @param message the message
     */
    public Log(String message) {
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
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Log{" +
                ", logID='" + logId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}