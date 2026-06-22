package com.homeapp.backend.models.bike;

import com.homeapp.backend.models.bike.Enums.BrakeType;
import com.homeapp.backend.models.bike.Enums.GroupsetBrand;
import com.homeapp.backend.models.bike.Enums.HandleBarType;
import com.homeapp.backend.models.bike.Enums.ShifterStyle;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The Full bike object, this pulls in all relevant information for a single bike.
 * Bikes are stored in JSON file.
 */
@Getter
@Setter
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
     * @param wheelPreference    the wheel preference, either Expensive or Cheap
     */
    public FullBike(String bikeName, Frame frame, BrakeType brakeType, GroupsetBrand groupsetBrand, HandleBarType handleBarType, long numberOfFrontGears, long numberOfRearGears, ShifterStyle shifterStyle, String wheelPreference) {
        this.frame = frame;
        this.bikeName = bikeName;
        this.brakeType = brakeType;
        this.groupsetBrand = groupsetBrand;
        this.handleBarType = handleBarType;
        this.numberOfFrontGears = numberOfFrontGears;
        this.numberOfRearGears = numberOfRearGears;
        this.shifterStyle = shifterStyle;
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