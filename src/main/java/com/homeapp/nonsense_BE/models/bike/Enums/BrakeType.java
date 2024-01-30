package com.homeapp.nonsense_BE.models.bike.Enums;

/**
 * The enum for Brake Types.
 */
public enum BrakeType {

    /**
     * The Mechanical disc.
     */
    MECHANICAL_DISC("Mechanical Disc"),
    /**
     * The Hydraulic disc.
     */
    HYDRAULIC_DISC("Hydraulic Disc"),
    /**
     * Rim brake type.
     */
    RIM("Rim"),
    /**
     * The Not required.
     */
    NOT_REQUIRED("Not Required"),
    /**
     * The No selection.
     */
    NO_SELECTION("No Selection");

    private final String name;

    private BrakeType(String name) {
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
     * From name brake type.
     *
     * @param name the name
     * @return the brake type
     */
    public static BrakeType fromName(String name) {
        return switch (name) {
            case "Hydraulic Disc" -> HYDRAULIC_DISC;
            case "Rim" -> RIM;
            case "Mechanical Disc" -> MECHANICAL_DISC;
            case "Not Required" -> NOT_REQUIRED;
            default -> NO_SELECTION;
        };
    }

}