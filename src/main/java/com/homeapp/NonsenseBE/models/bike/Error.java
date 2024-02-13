package com.homeapp.NonsenseBE.models.bike;

import javax.persistence.*;

/**
 * The Error object. Used by the Logger system to record issues throughout the project.
 */
@Entity
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partGen")
    @SequenceGenerator(name = "partGen", sequenceName = "PART_SEQ", allocationSize = 1)
    private long partId;

    private String component;

    private String method;

    private String errorCode;

    /**
     * Zero argument Constructor to Instantiate a new Error.
     */
    public Error() {
    }

    /**
     * Instantiates a new Error.
     *
     * @param component the component
     * @param method    the method
     * @param errorCode the error code
     */
    public Error(String component, String method, String errorCode) {
        this.component = component;
        this.method = method;
        this.errorCode = errorCode;
    }

    /**
     * Gets part id.
     *
     * @return the part id
     */
    public long getPartId() {
        return partId;
    }

    /**
     * Gets component.
     *
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * Sets component.
     *
     * @param component the component
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Error{" +
                "partId=" + partId +
                ", component='" + component + '\'' +
                ", message='" + method + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}