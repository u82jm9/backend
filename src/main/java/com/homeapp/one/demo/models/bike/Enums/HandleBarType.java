package com.homeapp.one.demo.models.bike.Enums;

public enum HandleBarType {

    DROPS("Drops"), FLAT("Flat"), BULLHORNS("Bullhorns"), FLARE("Flare");

    private String name;

    private HandleBarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HandleBarType fromName(String name) {
        if (name.equalsIgnoreCase("Drops")) {
            return DROPS;
        } else if (name.equalsIgnoreCase("Bullhorns")) {
            return BULLHORNS;
        } else if (name.equalsIgnoreCase("Flare")) {
            return FLARE;
        }
        return FLAT;
    }

}