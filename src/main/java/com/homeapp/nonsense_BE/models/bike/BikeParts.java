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

    public BigDecimal getTotalBikePrice() {
        return totalBikePrice;
    }

    public void setTotalBikePrice(BigDecimal totalBikePrice) {
        this.totalBikePrice = totalBikePrice;
    }

    @Override
    public String toString() {
        return "BikeParts{" +
                "bikePartsId=" + bikePartsId +
                "listOfParts=" + listOfParts +
                ", totalBikePrice=" + totalBikePrice +
                '}';
    }
}