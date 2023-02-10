package com.homeapp.one.demo.models.bike.Enums;

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
        if (name.equalsIgnoreCase("SRAM")) {
            return SRAM;
        } else if (name.equalsIgnoreCase("Campagnolo")) {
            return CAMPAGNOLO;
        } else if (name.equalsIgnoreCase("Shimano")) {
            return SHIMANO;
        } else return OTHER;
    }

}