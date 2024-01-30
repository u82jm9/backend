package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

/**
 * The Image object. Contains all the information the FE needs to display correct image.
 * Image files are stored in FE project.
 */
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

    /**
     * Instantiates a new Image.
     *
     * @param place     the place
     * @param component the component
     * @param src       the src
     * @param altText   the alt text
     */
    public Image(long place, String component, String src, String altText) {
        this.place = place;
        this.component = component;
        this.src = src;
        this.altText = altText;
    }

    /**
     * Gets image id.
     *
     * @return the image id
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Gets place.
     *
     * @return the place
     */
    public long getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param place the place
     */
    public void setPlace(long place) {
        this.place = place;
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
     * Gets src.
     *
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets src.
     *
     * @param src the src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Gets alt text.
     *
     * @return the alt text
     */
    public String getAltText() {
        return altText;
    }

    /**
     * Sets alt text.
     *
     * @param altText the alt text
     */
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