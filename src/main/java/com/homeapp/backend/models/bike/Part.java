package com.homeapp.backend.models.bike;

import javax.persistence.*;
import java.util.Date;

/**
 * The Part object, for a single complete Bike part.
 */
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partGen")
    @SequenceGenerator(name = "partGen", sequenceName = "PART_SEQ", allocationSize = 1)
    private long partId;

    private String component;

    private String internalReference;

    private String name;

    private String price;

    private String link;

    private Date dateLastUpdated;

    /**
     * Zero argument Constructor to Instantiate a new Part.
     */
    public Part() {
    }

    /**
     * Instantiates a new Part.
     *
     * @param component         the component
     * @param internalReference the internalReference, used to retrieve info about Part
     * @param name              the name
     * @param price             the price
     * @param link              the link
     * @param dateLastUpdated   the date this part last had it's price/name updated
     */
    public Part(String component, String internalReference, String name, String price, String link, Date dateLastUpdated) {
        this.component = component;
        this.internalReference = internalReference;
        this.name = name;
        this.price = price;
        this.link = link;
        this.dateLastUpdated = dateLastUpdated;
    }

    /**
     * Instantiates a new Part.
     *
     * @param component         the component
     * @param internalReference the internalReference
     * @param link              the link
     */
    public Part(String component, String internalReference, String link) {
        this.component = component;
        this.internalReference = internalReference;
        this.link = link;
    }

    /**
     * Gets part id.
     *
     * @return the part id
     */
    public long getPartId() {
        return partId;
    }

    /**
     * Gets component.
     *
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * Sets component.
     *
     * @param component the component
     */
    public void setComponent(String component) {
        this.component = component;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets internalReference.
     *
     * @return the internalReference
     */
    public String getInternalReference() {
        return internalReference;
    }

    /**
     * Sets internalReference.
     *
     * @param internalReference the internalReference
     */
    public void setInternalReference(String internalReference) {
        this.internalReference = Part.this.internalReference;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets dateLastUpdated.
     *
     * @return the dateLastUpdated
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * Sets dateLastUpdated.
     *
     * @param dateLastUpdated the dateLastUpdated
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    @Override
    public String toString() {
        return "Part{" +
                "partId='" + partId + '\'' +
                "component='" + component + '\'' +
                ", name='" + internalReference + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", dateLastUpdated='" + dateLastUpdated + '\'' +
                '}';
    }
}