package com.homeapp.NonsenseBE.models.bike;

import com.homeapp.NonsenseBE.models.bike.Enums.FrameStyle;

import javax.persistence.*;
import java.util.Objects;

/**
 * The Frame object. Used to simplify the Full Bike object to allow for more design options.
 */
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

    /**
     * Instantiates a new Frame.
     */
    public Frame() {
    }

    /**
     * Instantiates a new Frame.
     *
     * @param frameStyle             the frame style
     * @param discBrakeCompatible    the disc brake compatible
     * @param requiresFrontGearCable the requires front gear cable
     * @param requiresRearGearCable  the requires rear gear cable
     */
    public Frame(FrameStyle frameStyle, boolean discBrakeCompatible, boolean requiresFrontGearCable, boolean requiresRearGearCable) {
        this.frameStyle = frameStyle;
        this.discBrakeCompatible = discBrakeCompatible;
        this.requiresFrontGearCable = requiresFrontGearCable;
        this.requiresRearGearCable = requiresRearGearCable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return frameId == frame.frameId && size == frame.size && tireClearance == frame.tireClearance && discBrakeCompatible == frame.discBrakeCompatible && requiresFrontGearCable == frame.requiresFrontGearCable && requiresRearGearCable == frame.requiresRearGearCable && frameStyle == frame.frameStyle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameId, size, tireClearance, frameStyle, discBrakeCompatible, requiresFrontGearCable, requiresRearGearCable);
    }

    /**
     * Gets frame id.
     *
     * @return the frame id
     */
    public long getFrameId() {
        return frameId;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets tire clearance.
     *
     * @return the tire clearance
     */
    public long getTireClearance() {
        return tireClearance;
    }

    /**
     * Sets tire clearance.
     *
     * @param tireClearance the tire clearance
     */
    public void setTireClearance(long tireClearance) {
        this.tireClearance = tireClearance;
    }

    /**
     * Gets frame style.
     *
     * @return the frame style
     */
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    /**
     * Sets frame style.
     *
     * @param frameStyle the frame style
     */
    public void setFrameStyle(FrameStyle frameStyle) {
        this.frameStyle = frameStyle;
    }

    /**
     * Is disc brake compatible boolean.
     *
     * @return the boolean
     */
    public boolean isDiscBrakeCompatible() {
        return discBrakeCompatible;
    }

    /**
     * Sets disc brake compatible.
     *
     * @param discBrakeCompatible the disc brake compatible
     */
    public void setDiscBrakeCompatible(boolean discBrakeCompatible) {
        this.discBrakeCompatible = discBrakeCompatible;
    }

    /**
     * Is requires front gear cable boolean.
     *
     * @return the boolean
     */
    public boolean isRequiresFrontGearCable() {
        return requiresFrontGearCable;
    }

    /**
     * Sets requires front gear cable.
     *
     * @param requiresFrontGearCable the requires front gear cable
     */
    public void setRequiresFrontGearCable(boolean requiresFrontGearCable) {
        this.requiresFrontGearCable = requiresFrontGearCable;
    }

    /**
     * Is requires rear gear cable boolean.
     *
     * @return the boolean
     */
    public boolean isRequiresRearGearCable() {
        return requiresRearGearCable;
    }

    /**
     * Sets requires rear gear cable.
     *
     * @param requiresRearGearCable the requires rear gear cable
     */
    public void setRequiresRearGearCable(boolean requiresRearGearCable) {
        this.requiresRearGearCable = requiresRearGearCable;
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