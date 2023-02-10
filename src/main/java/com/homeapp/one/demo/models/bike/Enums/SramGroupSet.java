package com.homeapp.one.demo.models.bike.Enums;

public enum SramGroupSet {

    FORCE("Force"), RIVAL("Rival"), APEX("Apex"), EAGLE("Eagle"),
    RED("Red"), S_SERIES("S Series");

    private String name;

    private SramGroupSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SramGroupSet fromName(String name) {
        return switch (name) {
            case "Force" -> FORCE;
            case "Rival" -> RIVAL;
            case "Apex" -> APEX;
            case "Red" -> RED;
            case "S Series" -> S_SERIES;
            default -> EAGLE;
        };
    }

}