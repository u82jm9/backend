package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageGen")
    @SequenceGenerator(name = "imageGen", sequenceName = "IMAGE_SEQ", allocationSize = 1)
    private long imageId;

    private long place;

    private String component;

    private String src;

    private String altText;

    public Image() {
    }

    public Image(long place, String component, String src, String altText) {
        this.place = place;
        this.component = component;
        this.src = src;
        this.altText = altText;
    }

    public long getImageId() {
        return imageId;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                "place=" + place +
                ", component='" + component + '\'' +
                ", src='" + src + '\'' +
                ", altText='" + altText + '\'' +
                '}';
    }
}