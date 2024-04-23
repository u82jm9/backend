package com.homeapp.backend.models.bike;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bike Parts object, a collection of parts used to create a total bike price.
 * Used as a transfer object with FE to take relevant information about bike build, like price and error messages.
 */
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

    /**
     * Instantiates a new Bike parts.
     * Sets list of Parts to instance.
     */
    public BikeParts() {
        this.listOfParts = new ArrayList<>();
    }

    /**
     * Gets bike parts id.
     *
     * @return the bike parts id
     */
    public long getBikePartsId() {
        return bikePartsId;
    }

    /**
     * Gets list of parts.
     *
     * @return the list of parts
     */
    public List<Part> getListOfParts() {
        return listOfParts;
    }

    /**
     * Sets list of parts.
     *
     * @param listOfParts the list of parts
     */
    public void setListOfParts(List<Part> listOfParts) {
        this.listOfParts = listOfParts;
    }

    /**
     * Gets total price as string.
     *
     * @return the total price as string
     */
    public String getTotalPriceAsString() {
        return totalPriceAsString;
    }

    /**
     * Sets total price as string.
     *
     * @param totalPriceAsString the total price as string
     */
    public void setTotalPriceAsString(String totalPriceAsString) {
        this.totalPriceAsString = totalPriceAsString;
    }

    /**
     * Gets total bike price.
     *
     * @return the total bike price
     */
    public BigDecimal getTotalBikePrice() {
        return totalBikePrice;
    }

    /**
     * Sets total bike price.
     *
     * @param totalBikePrice the total bike price
     */
    public void setTotalBikePrice(BigDecimal totalBikePrice) {
        this.totalBikePrice = totalBikePrice;
    }

    /**
     * Gets error messages.
     *
     * @return the error messages
     */
    public List<Error> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Sets error messages.
     *
     * @param errorMessages the error messages
     */
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