package com.homeapp.one.demo.models.bike;

import com.homeapp.one.demo.models.bike.Enums.FrameStyle;
import com.homeapp.one.demo.models.bike.Enums.ShifterStyle;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = ("Frame"))
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FrameId")
    private long frameId;

    @Column
    private long size;

    @Column
    private long tireClearance;

    @Enumerated(EnumType.STRING)
    @Column
    private FrameStyle frameStyle;

    @Column
    private boolean discBrakeCompatible;

    @Column
    private boolean requiresFrontGearCable;

    @Column
    private boolean requiresRearGearCable;

    public Frame() {
    }

    public Frame(FrameStyle frameStyle, boolean discBrakeCompatible, boolean requiresFrontGearCable, boolean requiresRearGearCable) {
        this.frameStyle = frameStyle;
        this.discBrakeCompatible = discBrakeCompatible;
        this.requiresFrontGearCable = requiresFrontGearCable;
        this.requiresRearGearCable = requiresRearGearCable;
    }

    public long getFrameId() {
        return frameId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTireClearance() {
        return tireClearance;
    }

    public void setTireClearance(long tireClearance) {
        this.tireClearance = tireClearance;
    }

    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    public void setFrameStyle(FrameStyle frameStyle) {
        this.frameStyle = frameStyle;
    }

    public boolean isDiscBrakeCompatible() {
        return discBrakeCompatible;
    }

    public void setDiscBrakeCompatible(boolean discBrakeCompatible) {
        this.discBrakeCompatible = discBrakeCompatible;
    }

    public boolean isRequiresFrontGearCable() {
        return requiresFrontGearCable;
    }

    public void setRequiresFrontGearCable(boolean requiresFrontGearCable) {
        this.requiresFrontGearCable = requiresFrontGearCable;
    }

    public boolean isRequiresRearGearCable() {
        return requiresRearGearCable;
    }

    public void setRequiresRearGearCable(boolean requiresRearGearCable) {
        this.requiresRearGearCable = requiresRearGearCable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frame)) return false;
        Frame frame = (Frame) o;
        return getFrameId() == frame.getFrameId() && getSize() == frame.getSize() && getTireClearance() == frame.getTireClearance() && isDiscBrakeCompatible() == frame.isDiscBrakeCompatible() && isRequiresFrontGearCable() == frame.isRequiresFrontGearCable() && isRequiresRearGearCable() == frame.isRequiresRearGearCable() && getFrameStyle() == frame.getFrameStyle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrameId(), getSize(), getTireClearance(), getFrameStyle(), isDiscBrakeCompatible(), isRequiresFrontGearCable(), isRequiresRearGearCable());
    }

    @Override
    public String toString() {
        return "Frame{" +
                "frameId=" + frameId +
                ", size=" + size +
                ", tireClearance=" + tireClearance +
                ", frameStyle=" + frameStyle +
                ", discBrakeCompatible=" + discBrakeCompatible +
                ", requiresFrontGearCable=" + requiresFrontGearCable +
                ", requiresRearGearCable=" + requiresRearGearCable +
                '}';
    }
}