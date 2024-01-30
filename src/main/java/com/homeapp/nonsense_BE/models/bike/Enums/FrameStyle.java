package com.homeapp.nonsense_BE.models.bike.Enums;

/**
 * The enum for Frame styles.
 */
public enum FrameStyle {

    /**
     * The Single speed.
     */
    SINGLE_SPEED("Single Speed"),
    /**
     * Road frame style.
     */
    ROAD("Road"),
    /**
     * Gravel frame style.
     */
    GRAVEL("Gravel"),
    /**
     * Tour frame style.
     */
    TOUR("Tour"),
    /**
     * The None selected.
     */
    NONE_SELECTED("None Selected");

    private final String name;

    private FrameStyle(String name) {
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
     * From name frame style.
     *
     * @param name the name
     * @return the frame style
     */
    public static FrameStyle fromName(String name) {
        return switch (name) {
            case "Single Speed" -> SINGLE_SPEED;
            case "Road" -> ROAD;
            case "Gravel" -> GRAVEL;
            case "Tour" -> TOUR;
            default -> NONE_SELECTED;
        };
    }

}