package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

@Entity
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partGen")
    @SequenceGenerator(name = "partGen", sequenceName = "PART_SEQ", allocationSize = 1)
    private long partId;

    private String component;

    private String method;

    private String errorCode;

    public Error(String component, String method, String errorCode) {
        this.component = component;
        this.method = method;
        this.errorCode = errorCode;
    }

    public long getPartId() {
        return partId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getErrorCode() {
        return errorCode;
    }

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