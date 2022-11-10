package com.homeapp.one.demo.models;

import com.homeapp.one.demo.models.Enums.FrameStyle;
import com.homeapp.one.demo.models.Enums.ShifterStyle;

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

    @Column
    private FrameStyle frameStyle;

    @Column(nullable = false)
    private boolean discBrakeCompatible;

    @Column(nullable = false)
    private boolean requiresFrontGearCable;

    @Column(nullable = false)
    private boolean requiresRearGearCable;

    @Column(nullable = false)
    private ShifterStyle shifterStyle;

    public Frame() {
    }

    public Frame(FrameStyle frameStyle, boolean discBrakeCompatible, boolean requiresFrontGearCable, boolean requiresRearGearCable, ShifterStyle shifterStyle) {
        this.frameStyle = frameStyle;
        this.discBrakeCompatible = discBrakeCompatible;
        this.requiresFrontGearCable = requiresFrontGearCable;
        this.requiresRearGearCable = requiresRearGearCable;
        this.shifterStyle = shifterStyle;
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

    public ShifterStyle getShifterStyle() {
        return shifterStyle;
    }

    public void setShifterStyle(ShifterStyle shifterStyle) {
        this.shifterStyle = shifterStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frame)) return false;
        Frame frame = (Frame) o;
        return getFrameId() == frame.getFrameId() && getSize() == frame.getSize() && getTireClearance() == frame.getTireClearance() && isDiscBrakeCompatible() == frame.isDiscBrakeCompatible() && isRequiresFrontGearCable() == frame.isRequiresFrontGearCable() && isRequiresRearGearCable() == frame.isRequiresRearGearCable() && getFrameStyle() == frame.getFrameStyle() && getShifterStyle() == frame.getShifterStyle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrameId(), getSize(), getTireClearance(), getFrameStyle(), isDiscBrakeCompatible(), isRequiresFrontGearCable(), isRequiresRearGearCable(), getShifterStyle());
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
                ", shifterStyle=" + shifterStyle +
                '}';
    }
}