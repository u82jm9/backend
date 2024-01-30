package com.homeapp.nonsense_BE.models.bike;

import com.homeapp.nonsense_BE.models.bike.Enums.BrakeType;
import com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand;
import com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType;
import com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle;

import javax.persistence.*;

/**
 * The Full bike object, this pulls in all relevant information for a single bike.
 * Bikes are stored in JSON file.
 */
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

    /**
     * Instantiates a new Full bike.
     */
    public FullBike() {
    }

    /**
     * Instantiates a new Full bike.
     *
     * @param bikeName           the bike name
     * @param frame              the frame
     * @param brakeType          the brake type
     * @param groupsetBrand      the groupset brand
     * @param handleBarType      the handle bar type
     * @param numberOfFrontGears the number of front gears
     * @param numberOfRearGears  the number of rear gears
     * @param shifterStyle       the shifter style
     */
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

    /**
     * Sets full bike id.
     *
     * @param fullBikeId the full bike id
     */
    public void setFullBikeId(long fullBikeId) {
        this.fullBikeId = fullBikeId;
    }

    /**
     * Gets groupset brand.
     *
     * @return the groupset brand
     */
    public GroupsetBrand getGroupsetBrand() {
        return groupsetBrand;
    }

    /**
     * Sets groupset brand.
     *
     * @param groupsetBrand the groupset brand
     */
    public void setGroupsetBrand(GroupsetBrand groupsetBrand) {
        this.groupsetBrand = groupsetBrand;
    }

    /**
     * Gets full bike id.
     *
     * @return the full bike id
     */
    public long getFullBikeId() {
        return fullBikeId;
    }

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Sets frame.
     *
     * @param frame the frame
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    /**
     * Gets bike name.
     *
     * @return the bike name
     */
    public String getBikeName() {
        return bikeName;
    }

    /**
     * Sets bike name.
     *
     * @param bikeName the bike name
     */
    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    /**
     * Gets brake type.
     *
     * @return the brake type
     */
    public BrakeType getBrakeType() {
        return brakeType;
    }

    /**
     * Sets brake type.
     *
     * @param brakeType the brake type
     */
    public void setBrakeType(BrakeType brakeType) {
        this.brakeType = brakeType;
    }

    /**
     * Gets handle bar type.
     *
     * @return the handle bar type
     */
    public HandleBarType getHandleBarType() {
        return handleBarType;
    }

    /**
     * Sets handle bar type.
     *
     * @param handleBarType the handle bar type
     */
    public void setHandleBarType(HandleBarType handleBarType) {
        this.handleBarType = handleBarType;
    }

    /**
     * Gets number of front gears.
     *
     * @return the number of front gears
     */
    public long getNumberOfFrontGears() {
        return numberOfFrontGears;
    }

    /**
     * Sets number of front gears.
     *
     * @param numberOfFrontGears the number of front gears
     */
    public void setNumberOfFrontGears(long numberOfFrontGears) {
        this.numberOfFrontGears = numberOfFrontGears;
    }

    /**
     * Gets number of rear gears.
     *
     * @return the number of rear gears
     */
    public long getNumberOfRearGears() {
        return numberOfRearGears;
    }

    /**
     * Sets number of rear gears.
     *
     * @param numberOfRearGears the number of rear gears
     */
    public void setNumberOfRearGears(long numberOfRearGears) {
        this.numberOfRearGears = numberOfRearGears;
    }

    /**
     * Gets shifter style.
     *
     * @return the shifter style
     */
    public ShifterStyle getShifterStyle() {
        return shifterStyle;
    }

    /**
     * Sets shifter style.
     *
     * @param shifterStyle the shifter style
     */
    public void setShifterStyle(ShifterStyle shifterStyle) {
        this.shifterStyle = shifterStyle;
    }

    /**
     * Gets wheel preference.
     *
     * @return the wheel preference
     */
    public String getWheelPreference() {
        return wheelPreference;
    }

    /**
     * Sets wheel preference.
     *
     * @param wheelPreference the wheel preference
     */
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