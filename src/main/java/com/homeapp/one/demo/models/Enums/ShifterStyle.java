package com.homeapp.one.demo.models.Enums;

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
        if (name.equalsIgnoreCase("Trigger")) {
            return TRIGGER;
        } else if (name.equalsIgnoreCase("STI")) {
            return STI;
        } else return NONE;
    }
}