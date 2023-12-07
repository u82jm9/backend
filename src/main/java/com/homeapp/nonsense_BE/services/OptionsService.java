package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.CombinedData;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;

@Service
public class OptionsService {

    private static final Logger LOGGER = LogManager.getLogger(OptionsService.class);

    private static OptionsService instance;
    private Options options;

    private OptionsService() {
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
        LOGGER.info("Getting Options for a new Bike!");
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
        return o;
    }

    public Options updateOptions(CombinedData combinedData) {
        Options o = combinedData.getOptions();
        setOptions(o);
        if (!o.isShowFrameStyles()) {
            getGearOptions(combinedData.getBike());
            getBarOptions(combinedData.getBike());
            getBrakeOptions(combinedData.getBike());
        }
        return o;
    }

    public void getGearOptions(FullBike b) {
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