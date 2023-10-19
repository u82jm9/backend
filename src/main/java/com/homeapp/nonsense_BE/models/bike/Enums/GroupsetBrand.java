package com.homeapp.nonsense_BE.models.bike.Enums;

public enum GroupsetBrand {

    CAMPAGNOLO("Campagnolo"), SRAM("SRAM"), SHIMANO("Shimano"), OTHER("Other");

    private String name;

    private GroupsetBrand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GroupsetBrand fromName(String name) {
        return switch (name) {
            case "SRAM" -> SRAM;
            case "Campagnolo" -> CAMPAGNOLO;
            case "Shimano" -> SHIMANO;
            default -> OTHER;
        };
    }
}