package com.homeapp.one.demo.models.bike.Enums;

public enum CampagnoloGroupSet {

    SUPER_RECORD("Super Record"), RECORD("Record"), CHORUS("Chorus"), CENTAUR("Eagle"),
    EKAR("Ekar");

    private String name;

    private CampagnoloGroupSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CampagnoloGroupSet fromName(String name) {
        return switch (name) {
            case "Super Record" -> SUPER_RECORD;
            case "Record" -> RECORD;
            case "Chorus" -> CHORUS;
            case "Ekar" -> EKAR;
            default -> CENTAUR;
        };
    }

}