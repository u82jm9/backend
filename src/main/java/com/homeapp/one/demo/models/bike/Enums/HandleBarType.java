package com.homeapp.one.demo.models.bike.Enums;

public enum HandleBarType {

    DROPS("Drops"), FLAT("Flat"), BULLHORNS("Bullhorns"), FLARE("Flare"), NOT_SELECTED("Not Selected");

    private String name;

    private HandleBarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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