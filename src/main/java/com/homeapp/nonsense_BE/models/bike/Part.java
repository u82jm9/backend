package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partGen")
    @SequenceGenerator(name = "partGen", sequenceName = "PART_SEQ", allocationSize = 1)
    private long partId;

    private String component;

    private String name;

    private BigDecimal price;

    private String link;

    public Part(String component, String name, BigDecimal price, String link) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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