package com.homeapp.one.demo.models.Enums;

public enum FrameStyle {

    CITY("City"), ROAD("Road"), GRAVEL("Gravel"), TOUR("Tour"),
    HARD_TAIL("Hard Tail"), FULL_SUSPENSION("Full Suspension");

    private String name;

    private FrameStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FrameStyle fromName(String name) {
        return switch (name) {
            case "City" -> CITY;
            case "Road" -> ROAD;
            case "Gravel" -> GRAVEL;
            case "Hard Tail" -> HARD_TAIL;
            case "Full Suspension" -> FULL_SUSPENSION;
            default -> TOUR;
        };
    }

}