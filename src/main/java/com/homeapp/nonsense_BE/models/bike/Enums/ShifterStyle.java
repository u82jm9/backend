package com.homeapp.nonsense_BE.models.bike.Enums;

/**
 * The enum for Shifter styles.
 */
public enum ShifterStyle {

    /**
     * Sti shifter style.
     */
    STI("STI"),
    /**
     * Trigger shifter style.
     */
    TRIGGER("Trigger"),
    /**
     * None shifter style.
     */
    NONE("None");

    private final String name;

    private ShifterStyle(String name) {
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
     * From name shifter style.
     *
     * @param name the name
     * @return the shifter style
     */
    public static ShifterStyle fromName(String name) {
        return switch (name) {
            case "Trigger" -> TRIGGER;
            case "STI" -> STI;
            default -> NONE;
        };
    }
}