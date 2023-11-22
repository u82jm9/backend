package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

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

    public Part(String component, String name, String price, String link) {
        this.component = component;
        this.name = name;
        this.price = price;
        this.link = link;
    }

    public long getPartId() {
        return partId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

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