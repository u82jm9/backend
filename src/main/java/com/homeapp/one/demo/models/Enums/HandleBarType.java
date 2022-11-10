package com.homeapp.one.demo.models.Enums;

public enum HandleBarType {

    DROPS("Drops"), FLAT("Flat"), BULLHORNS("Bullhorns");

    private String name;

    private HandleBarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HandleBarType fromName(String name) {
        if (name == "Drops") {
            return DROPS;
        } else if (name=="Bullhorns") {
            return BULLHORNS;
        }
        return FLAT;
    }

}