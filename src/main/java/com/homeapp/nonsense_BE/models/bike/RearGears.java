package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

/**
 * The Rear Gears. Simplified due to only single type of components, currently not supporting SRAM or Campagnolo
 */
@Entity
@Table
public class RearGears {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rearGearsGen")
    @SequenceGenerator(name = "rearGearsGen", sequenceName = "REAR_GEARS_SEQ", allocationSize = 1)
    @Column(name = "RearGearsId")
    private long rearGearsId;

    @Column
    private long numberOfGears;

    /**
     * Instantiates a new Rear gears.
     */
    public RearGears() {
    }

    /**
     * Instantiates a new Rear gears.
     *
     * @param numberOfGears the number of gears
     */
    public RearGears(long numberOfGears) {
        this.numberOfGears = numberOfGears;

    }

    /**
     * Gets rear gears id.
     *
     * @return the rear gears id
     */
    public long getRearGearsId() {
        return rearGearsId;
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
        return "RearGears{" +
                "rearGearsId=" + rearGearsId +
                ", numberOfGears=" + numberOfGears +
                '}';
    }
}