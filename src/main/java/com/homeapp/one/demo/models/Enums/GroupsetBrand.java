package com.homeapp.one.demo.models.Enums;

public enum GroupsetBrand {

    CAMPAGNOLO("Campagnolo"), SRAM("SRAM"), SHIMANO("Shimano");

    private String name;

    private GroupsetBrand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GroupsetBrand fromName(String name) {
        if (name == "SRAM") {
            return SRAM;
        } else if (name == "Campagnolo") {
            return CAMPAGNOLO;
        } else return SHIMANO;
    }

}