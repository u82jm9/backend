package com.homeapp.one.demo.models.Enums;

public enum BrakeType {

    MECHANICAL_DISC("Mechanical Disc"), HYDRAULIC_DISC("Hydraulic Disc"), RIM("Rim");

    private String name;

    private BrakeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BrakeType fromName(String name) {
        if (name == "Hydraulic Disc") {
            return HYDRAULIC_DISC;
        } else if (name == "Mechanical Disc") {
            return MECHANICAL_DISC;
        } else return RIM;
    }

}