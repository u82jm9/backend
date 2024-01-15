package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BikeParts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bikePartsGen")
    @SequenceGenerator(name = "bikePartsGen", sequenceName = "BIKE_PARTS_SEQ", allocationSize = 1)
    private long bikePartsId;

    @OneToMany
    private List<Part> listOfParts;

    private BigDecimal totalBikePrice;

    private String totalPriceAsString = "";

    @OneToMany
    private List<Error> errorMessages = new ArrayList<>();

    public BikeParts() {
        this.listOfParts = new ArrayList<>();
    }

    public long getBikePartsId() {
        return bikePartsId;
    }

    public List<Part> getListOfParts() {
        return listOfParts;
    }

    public void setListOfParts(List<Part> listOfParts) {
        this.listOfParts = listOfParts;
    }

    public String getTotalPriceAsString() {
        return totalPriceAsString;
    }

    public void setTotalPriceAsString(String totalPriceAsString) {
        this.totalPriceAsString = totalPriceAsString;
    }

    public BigDecimal getTotalBikePrice() {
        return totalBikePrice;
    }

    public void setTotalBikePrice(BigDecimal totalBikePrice) {
        this.totalBikePrice = totalBikePrice;
    }

    public List<Error> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<Error> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "BikeParts{" +
                "bikePartsId=" + bikePartsId +
                "listOfParts=" + listOfParts +
                ", totalBikePrice=" + totalBikePrice +
                ", totalPriceAsString=" + totalPriceAsString +
                ", errorMessages=" + errorMessages +
                '}';
    }
}