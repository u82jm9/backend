package com.homeapp.one.demo.models.bike.Enums;

public enum BrakeType {

    MECHANICAL_DISC("Mechanical Disc"), HYDRAULIC_DISC("Hydraulic Disc"), RIM("Rim"), NOT_REQUIRED("Not Required");

    private String name;

    private BrakeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BrakeType fromName(String name) {
        return switch (name) {
            case "Hydraulic Disc" -> HYDRAULIC_DISC;
            case "Rim" -> RIM;
            case "Mechanical Disc" -> MECHANICAL_DISC;
            default -> NOT_REQUIRED;
        };
    }

}