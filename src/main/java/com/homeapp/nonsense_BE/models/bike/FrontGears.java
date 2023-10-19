package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;

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

    public FrontGears() {
    }

    public FrontGears(long numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    public long getFrontGearsId() {
        return frontGearsId;
    }

    public long getNumberOfGears() {
        return numberOfGears;
    }

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