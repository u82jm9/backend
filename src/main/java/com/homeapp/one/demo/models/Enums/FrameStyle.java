package com.homeapp.one.demo.models.Enums;

public enum FrameStyle {

    SINGLE_SPEED("Single Speed"), ROAD("Road"), GRAVEL("Gravel"), TOUR("Tour"), NONE_SELECTED("None Selected");

    private String name;

    private FrameStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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