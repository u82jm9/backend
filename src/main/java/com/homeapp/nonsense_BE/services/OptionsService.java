package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.CombinedData;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Options;
import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;

@Service
public class OptionsService {

    private static OptionsService instance;
    private Options options;
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();

    @Autowired
    public OptionsService() {
        this.options = new Options();
    }

    public static OptionsService getInstance() {
        if (instance == null) {
            instance = new OptionsService();
        }
        return instance;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public Options startNewBike() {
        infoLogger.log("Getting Options for a new Bike!");
        Options o = new Options();
        o.getGroupsetBrand().add(SHIMANO.getName());
        o.setShowGroupSetBrand(true);
        o.getFrameSizes().add(48L);
        o.getFrameSizes().add(50L);
        o.getFrameSizes().add(52L);
        o.getFrameSizes().add(54L);
        o.getFrameSizes().add(56L);
        o.setShowFrameSizes(true);
        o.getFrameStyles().add(SINGLE_SPEED.getName());
        o.getFrameStyles().add(GRAVEL.getName());
        o.getFrameStyles().add(TOUR.getName());
        o.getFrameStyles().add(ROAD.getName());
        o.setShowFrameStyles(true);
        warnLogger.log("Returning options: " + o);
        return o;
    }

    public Options updateOptions(CombinedData combinedData) {
        infoLogger.log("Updating Options available for Bike");
        Options o = combinedData.getOptions();
        setOptions(o);
        if (!o.isShowFrameStyles()) {
            getGearOptions(combinedData.getBike());
            getBarOptions(combinedData.getBike());
            getBrakeOptions(combinedData.getBike());
            getWheelOptions(combinedData.getBike());
        }
        warnLogger.log("Returning options: " + o);
        return o;
    }

    private void getWheelOptions(FullBike b) {
        infoLogger.log("Getting Wheel Options");
        List<String> wheelPreference = new ArrayList<>();
        Options o = getOptions();
        wheelPreference.add("Cheap");
        wheelPreference.add("Expensive");
        o.setWheelPreference(wheelPreference);
        if (b.getWheelPreference() == null) {
            o.setShowWheelPreference(true);
        }
    }

    public void getGearOptions(FullBike b) {
        infoLogger.log("Getting Gear Options");
        List<Long> rearGears = new ArrayList<>();
        List<Long> frontGears = new ArrayList<>();
        Options o = getOptions();
        if (!b.getHandleBarType().equals(FLAT)) {
            switch (b.getFrame().getFrameStyle()) {
                case ROAD -> {
                    rearGears.add(10L);
                    rearGears.add(11L);
                    rearGears.add(12L);
                    frontGears.add(2L);
                }
                case TOUR -> {
                    rearGears.add(11L);
                    rearGears.add(10L);
                    rearGears.add(9L);
                    frontGears.add(2L);
                    frontGears.add(3L);
                }
                case GRAVEL -> {
                    rearGears.add(9L);
                    rearGears.add(10L);
                    rearGears.add(11L);
                    frontGears.add(2L);
                    frontGears.add(1L);
                }
                default -> {
                }
            }
        } else {
            rearGears.add(10L);
            rearGears.add(11L);
            frontGears.add(1L);
        }
        if (b.getBrakeType().equals(HYDRAULIC_DISC)) {
            frontGears.removeIf(item -> item == 2L);
            frontGears.removeIf(item -> item == 3L);
        }
        o.setNumberOfFrontGears(frontGears);
        o.setNumberOfRearGears(rearGears);
        if (!b.getFrame().getFrameStyle().equals(SINGLE_SPEED)) {
            if (b.getNumberOfFrontGears() == 0) {
                o.setShowFrontGears(true);
            }
            if (b.getNumberOfRearGears() == 0) {
                o.setShowRearGears(true);
            }
        }
    }

    public void getBarOptions(FullBike b) {
        infoLogger.log("Getting Bar Options");
        List<String> bars = new ArrayList<>();
        Options o = getOptions();
        if (b.getHandleBarType().equals(NOT_SELECTED)) {
            o.setShowBarStyles(true);
        }
        bars.add(DROPS.getName());
        switch (b.getFrame().getFrameStyle()) {
            case SINGLE_SPEED -> {
                bars.add(BULLHORNS.getName());
                bars.add(FLAT.getName());
            }
            case TOUR -> {
                bars.add(FLARE.getName());
                bars.add(FLAT.getName());
            }
            case GRAVEL -> {
                bars.add(FLARE.getName());
            }
            default -> {
            }
        }
        o.setBarStyles(bars);
    }

    public void getBrakeOptions(FullBike b) {
        infoLogger.log("Getting Brake Options");
        List<String> brakes = new ArrayList<>();
        Options o = getOptions();
        if (b.getBrakeType().equals(NO_SELECTION)) {
            o.setShowBrakeStyles(true);
        }
        brakes.add(RIM.getName());
        if (!b.getFrame().getFrameStyle().equals(SINGLE_SPEED)) {
            brakes.add(MECHANICAL_DISC.getName());
            brakes.add(HYDRAULIC_DISC.getName());
        } else {
            brakes.add(NOT_REQUIRED.getName());
        }
        o.setBrakeStyles(brakes);
    }
}