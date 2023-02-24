package com.homeapp.one.demo.models.bike;

import com.homeapp.one.demo.models.bike.Enums.BrakeType;
import com.homeapp.one.demo.models.bike.Enums.GroupsetBrand;
import com.homeapp.one.demo.models.bike.Enums.HandleBarType;
import com.homeapp.one.demo.models.bike.Enums.ShifterStyle;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "Brakes")
    private BrakeType brakeType;

    @Column
    private GroupsetBrand groupsetBrand;
    @Enumerated(EnumType.STRING)
    @Column(name = "Bars")
    private HandleBarType handleBarType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FrontGearsId")
    private FrontGears frontGears;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RearGearsId")
    private RearGears rearGears;

    @Enumerated(EnumType.STRING)
    @Column
    private ShifterStyle shifterStyle;

    public FullBike() {
    }

    public FullBike(String bikeName, Frame frame, BrakeType brakeType, GroupsetBrand groupsetBrand, HandleBarType handleBarType, FrontGears frontGears, RearGears rearGears, ShifterStyle shifterStyle) {
        this.frame = frame;
        this.bikeName = bikeName;
        this.brakeType = brakeType;
        this.groupsetBrand = groupsetBrand;
        this.handleBarType = handleBarType;
        this.frontGears = frontGears;
        this.rearGears = rearGears;
        this.shifterStyle = shifterStyle;
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

    public ShifterStyle getShifterStyle() {
        return shifterStyle;
    }

    public void setShifterStyle(ShifterStyle shifterStyle) {
        this.shifterStyle = shifterStyle;
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
                ", shifterStyle=" + shifterStyle +
                '}';
    }
}