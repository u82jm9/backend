package com.homeapp.backend.models.bike.Enums;

/**
 * The enum for Handle Bar types.
 */
public enum HandleBarType {

    /**
     * Drops handle bar type.
     */
    DROPS("Drops"),
    /**
     * Flat handle bar type.
     */
    FLAT("Flat"),
    /**
     * Bullhorns handle bar type.
     */
    BULLHORNS("Bullhorns"),
    /**
     * Flare handle bar type.
     */
    FLARE("Flare"),
    /**
     * The Not selected.
     */
    NOT_SELECTED("Not Selected");

    private final String name;

    private HandleBarType(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * From name handle bar type.
     *
     * @param name the name
     * @return the handle bar type
     */
    public static HandleBarType fromName(String name) {
        return switch (name) {
            case "Drops" -> DROPS;
            case "Bullhorns" -> BULLHORNS;
            case "Flare" -> FLARE;
            case "Flat" -> FLAT;
            default -> NOT_SELECTED;
        };
    }
}