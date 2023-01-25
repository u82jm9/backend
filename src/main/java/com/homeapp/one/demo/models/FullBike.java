package com.homeapp.one.demo.models;

import com.homeapp.one.demo.models.Enums.BrakeType;
import com.homeapp.one.demo.models.Enums.GroupsetBrand;
import com.homeapp.one.demo.models.Enums.HandleBarType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = ("FullBike"))
public class FullBike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FullBikeId")
    private long fullBikeId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FrameId")
    private Frame frame;

    @Column(name = "BikeName")
    private String bikeName;

    @Column(name = "Brakes")
    private BrakeType brakeType;

    @Column(nullable = false)
    private GroupsetBrand groupsetBrand;

    @Column(name = "Bars")
    private HandleBarType handleBarType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FrontGearsId")
    private FrontGears frontGears;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RearGearsId")
    private RearGears rearGears;

    public FullBike() {
    }

    public FullBike(String bikeName, Frame frame, BrakeType brakeType, GroupsetBrand groupsetBrand, HandleBarType handleBarType, FrontGears frontGears, RearGears rearGears) {
        this.frame = frame;
        this.bikeName = bikeName;
        this.brakeType = brakeType;
        this.groupsetBrand = groupsetBrand;
        this.handleBarType = handleBarType;
        this.frontGears = frontGears;
        this.rearGears = rearGears;
    }

    public GroupsetBrand getGroupsetBrand() {
        return groupsetBrand;
    }

    public void setGroupsetBrand(GroupsetBrand groupsetBrand) {
        this.groupsetBrand = groupsetBrand;
    }

    public long getFullBikeId() {
        return fullBikeId;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public BrakeType getBrakeType() {
        return brakeType;
    }

    public void setBrakeType(BrakeType handlebarType) {
        this.brakeType = brakeType;
    }

    public HandleBarType getHandleBarType() {
        return handleBarType;
    }

    public void setHandleBarType(HandleBarType handleBarType) {
        this.handleBarType = handleBarType;
    }

    public FrontGears getFrontGears() {
        return frontGears;
    }

    public void setFrontGears(FrontGears frontGears) {
        this.frontGears = frontGears;
    }

    public RearGears getRearGears() {
        return rearGears;
    }

    public void setRearGears(RearGears rearGears) {
        this.rearGears = rearGears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullBike)) return false;
        FullBike fullBike = (FullBike) o;
        return getFullBikeId() == fullBike.getFullBikeId() && Objects.equals(getFrame(), fullBike.getFrame()) && Objects.equals(getBikeName(), fullBike.getBikeName()) && getBrakeType() == fullBike.getBrakeType() && getGroupsetBrand() == fullBike.getGroupsetBrand() && getHandleBarType() == fullBike.getHandleBarType() && Objects.equals(getFrontGears(), fullBike.getFrontGears()) && Objects.equals(getRearGears(), fullBike.getRearGears());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullBikeId(), getFrame(), getBikeName(), getBrakeType(), getGroupsetBrand(), getHandleBarType(), getFrontGears(), getRearGears());
    }

    @Override
    public String toString() {
        return "FullBike{" +
                "fullBikeId=" + fullBikeId +
                ", frame=" + frame +
                ", bikeName='" + bikeName + '\'' +
                ", brakeType=" + brakeType +
                ", groupsetBrand=" + groupsetBrand +
                ", handleBarType=" + handleBarType +
                ", frontGears=" + frontGears +
                ", rearGears=" + rearGears +
                '}';
    }
}