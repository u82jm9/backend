package com.homeapp.NonsenseBE.models.bike.Enums;

/**
 * The enum for Groupset brands.
 */
public enum GroupsetBrand {

    /**
     * Campagnolo groupset brand.
     */
    CAMPAGNOLO("Campagnolo"),
    /**
     * Sram groupset brand.
     */
    SRAM("SRAM"),
    /**
     * Shimano groupset brand.
     */
    SHIMANO("Shimano"),
    /**
     * Other groupset brand.
     */
    OTHER("Other");

    private final String name;

    private GroupsetBrand(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * From name groupset brand.
     *
     * @param name the name
     * @return the groupset brand
     */
    public static GroupsetBrand fromName(String name) {
        return switch (name) {
            case "SRAM" -> SRAM;
            case "Campagnolo" -> CAMPAGNOLO;
            case "Shimano" -> SHIMANO;
            default -> OTHER;
        };
    }
}