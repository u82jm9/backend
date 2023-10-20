package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BikeParts")
public class BikeParts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PartsId")
    private long partsId;

    @Column(name = "ListofParts")
    private List<Part> listOfParts;

    @Column(name = "TotalBikePrice")
    private BigDecimal totalBikePrice;

    public BikeParts() {
    }

    public long getPartsId() {
        return partsId;
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
                "partsId=" + partsId +
                "listOfParts=" + listOfParts +
                ", totalBikePrice=" + totalBikePrice +
                '}';
    }
}