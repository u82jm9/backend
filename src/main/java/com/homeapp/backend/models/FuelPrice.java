package com.homeapp.backend.models;

import lombok.Getter;
import lombok.Setter;

/**
 * An object that can be filled by the scraper and stored as a java object.
 */
@Getter
@Setter
public class FuelPrice {

    private String brand;
    private String fuelType;
    private String price;
    private String dateUpdated;

    public FuelPrice() {
    }

    @Override
    public String toString() {
        return "FuelPrice{" +
                "brand='" + brand + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", price='" + price + '\'' +
                ", date='" + dateUpdated + '\'' +
                '}';
    }
}