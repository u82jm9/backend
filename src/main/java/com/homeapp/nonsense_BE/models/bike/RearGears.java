package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

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

    public RearGears() {
    }

    public RearGears(long numberOfGears) {
        this.numberOfGears = numberOfGears;

    }

    public long getRearGearsId() {
        return rearGearsId;
    }

    public long getNumberOfGears() {
        return numberOfGears;
    }

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