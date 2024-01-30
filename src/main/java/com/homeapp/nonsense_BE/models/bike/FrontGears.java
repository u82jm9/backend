package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

/**
 * The Front Gears object. Simplified due to only single type of components, currently not supporting SRAM or Campagnolo
 */
@Entity
@Table
public class FrontGears {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frontGearsGen")
    @SequenceGenerator(name = "frontGearsGen", sequenceName = "FRONT_GEARS_SEQ", allocationSize = 1)
    @Column(name = "FrontGearsId")
    private long frontGearsId;

    @Column
    private long numberOfGears;

    /**
     * Instantiates a new Front gears.
     */
    public FrontGears() {
    }

    /**
     * Instantiates a new Front gears.
     *
     * @param numberOfGears the number of gears
     */
    public FrontGears(long numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    /**
     * Gets front gears id.
     *
     * @return the front gears id
     */
    public long getFrontGearsId() {
        return frontGearsId;
    }

    /**
     * Gets number of gears.
     *
     * @return the number of gears
     */
    public long getNumberOfGears() {
        return numberOfGears;
    }

    /**
     * Sets number of gears.
     *
     * @param numberOfGears the number of gears
     */
    public void setNumberOfGears(long numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    @Override
    public String toString() {
        return "FrontGears{" +
                "frontGearsId=" + frontGearsId +
                ", numberOfGears=" + numberOfGears +
                '}';
    }
}