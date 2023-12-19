package com.homeapp.nonsense_BE.models.bike;

import com.homeapp.nonsense_BE.models.bike.Enums.BrakeType;
import com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand;
import com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType;
import com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle;

import javax.persistence.*;

@Entity
@Table(name = ("FullBike"))
public class FullBike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FullBikeId")
    private long fullBikeId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FrameId")
    private Frame frame;

    @Column(name = "BikeName")
    private String bikeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "Brakes")
    private BrakeType brakeType;

    @Enumerated(EnumType.STRING)
    @Column
    private GroupsetBrand groupsetBrand;

    @Enumerated(EnumType.STRING)
    @Column(name = "Bars")
    private HandleBarType handleBarType;

    @Column
    private long numberOfFrontGears;

    @Column
    private long numberOfRearGears;

    @Enumerated(EnumType.STRING)
    @Column
    private ShifterStyle shifterStyle;

    @Column
    private String wheelPreference;

    public FullBike() {
    }

    public FullBike(String bikeName, Frame frame, BrakeType brakeType, GroupsetBrand groupsetBrand, HandleBarType handleBarType, long numberOfFrontGears, long numberOfRearGears, ShifterStyle shifterStyle) {
        this.frame = frame;
        this.bikeName = bikeName;
        this.brakeType = brakeType;
        this.groupsetBrand = groupsetBrand;
        this.handleBarType = handleBarType;
        this.numberOfFrontGears = numberOfFrontGears;
        this.numberOfRearGears = numberOfRearGears;
        this.shifterStyle = shifterStyle;
    }

    public void setFullBikeId(long fullBikeId) {
        this.fullBikeId = fullBikeId;
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

    public void setBrakeType(BrakeType brakeType) {
        this.brakeType = brakeType;
    }

    public HandleBarType getHandleBarType() {
        return handleBarType;
    }

    public void setHandleBarType(HandleBarType handleBarType) {
        this.handleBarType = handleBarType;
    }

    public long getNumberOfFrontGears() {
        return numberOfFrontGears;
    }

    public void setNumberOfFrontGears(long numberOfFrontGears) {
        this.numberOfFrontGears = numberOfFrontGears;
    }

    public long getNumberOfRearGears() {
        return numberOfRearGears;
    }

    public void setNumberOfRearGears(long numberOfRearGears) {
        this.numberOfRearGears = numberOfRearGears;
    }

    public ShifterStyle getShifterStyle() {
        return shifterStyle;
    }

    public void setShifterStyle(ShifterStyle shifterStyle) {
        this.shifterStyle = shifterStyle;
    }

    public String getWheelPreference() {
        return wheelPreference;
    }

    public void setWheelPreference(String wheelPreference) {
        this.wheelPreference = wheelPreference;
    }

    @Override
    public String toString() {
        return "FullBike{" +
                "fullBikeId=" + fullBikeId +
                ", frame=" + frame +
                ", bikeName='" + bikeName +
                ", brakeType=" + brakeType +
                ", groupsetBrand=" + groupsetBrand +
                ", handleBarType=" + handleBarType +
                ", numberOfFrontGears=" + numberOfFrontGears +
                ", numberOfRearGears=" + numberOfRearGears +
                ", shifterStyle=" + shifterStyle +
                ", wheelPreference=" + wheelPreference +
                '}';
    }
}