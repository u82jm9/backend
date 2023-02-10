package com.homeapp.one.demo.models.bike;

import com.homeapp.one.demo.models.bike.Enums.*;

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

    @Column
    private CampagnoloGroupSet campagnoloGroupSet;

    @Column
    private SramGroupSet sramGroupSet;

    @Column
    private ShimanoGroupSet shimanoGroupSet;

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

    public CampagnoloGroupSet getCampagnoloGroupSet() {
        return campagnoloGroupSet;
    }

    public void setCampagnoloGroupSet(CampagnoloGroupSet campagnoloGroupSet) {
        this.campagnoloGroupSet = campagnoloGroupSet;
    }

    public SramGroupSet getSramGroupSet() {
        return sramGroupSet;
    }

    public void setSramGroupSet(SramGroupSet sramGroupSet) {
        this.sramGroupSet = sramGroupSet;
    }

    public ShimanoGroupSet getShimanoGroupSet() {
        return shimanoGroupSet;
    }

    public void setShimanoGroupSet(ShimanoGroupSet shimanoGroupSet) {
        this.shimanoGroupSet = shimanoGroupSet;
    }

    @Override
    public String toString() {
        return "FrontGears{" +
                "frontGearsId=" + frontGearsId +
                ", numberOfGears=" + numberOfGears +
                ", campagnoloGroupSet=" + campagnoloGroupSet +
                ", sramGroupSet=" + sramGroupSet +
                ", shimanoGroupSet=" + shimanoGroupSet +
                '}';
    }
}