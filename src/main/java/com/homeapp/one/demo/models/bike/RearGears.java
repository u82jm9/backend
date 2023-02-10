package com.homeapp.one.demo.models.bike;

import com.homeapp.one.demo.models.bike.Enums.CampagnoloGroupSet;
import com.homeapp.one.demo.models.bike.Enums.ShimanoGroupSet;
import com.homeapp.one.demo.models.bike.Enums.SramGroupSet;

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

    @Column
    private CampagnoloGroupSet campagnoloGroupSet;

    @Column
    private SramGroupSet sramGroupSet;

    @Column
    private ShimanoGroupSet shimanoGroupSet;

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
        return "RearGears{" +
                "rearGearsId=" + rearGearsId +
                ", numberOfGears=" + numberOfGears +
                ", campagnoloGroupSet=" + campagnoloGroupSet +
                ", sramGroupSet=" + sramGroupSet +
                ", shimanoGroupSet=" + shimanoGroupSet +
                '}';
    }
}