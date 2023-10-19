package com.homeapp.nonsense_BE.models.bike.Enums;

public enum ShifterStyle {

    STI("STI"), TRIGGER("Trigger"), NONE("None");

    private String name;

    private ShifterStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ShifterStyle fromName(String name) {
        return switch (name) {
            case "Trigger" -> TRIGGER;
            case "STI" -> STI;
            default -> NONE;
        };
    }
}