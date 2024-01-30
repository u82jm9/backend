package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

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

    private String name;

    private String price;

    private String link;

    /**
     * Instantiates a new Part.
     *
     * @param component the component
     * @param name      the name
     * @param price     the price
     * @param link      the link
     */
    public Part(String component, String name, String price, String link) {
        this.component = component;
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "Part{" +
                "partId='" + partId + '\'' +
                "component='" + component + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
                '}';
    }
}