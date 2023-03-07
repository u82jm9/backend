package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.bike.Enums.*;
import com.homeapp.one.demo.models.bike.Frame;
import com.homeapp.one.demo.models.bike.FullBike;
import com.homeapp.one.demo.repository.FullBikeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.homeapp.one.demo.models.bike.Enums.BrakeType.NOT_REQUIRED;
import static com.homeapp.one.demo.models.bike.Enums.BrakeType.RIM;
import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.SINGLE_SPEED;
import static com.homeapp.one.demo.models.bike.Enums.HandleBarType.*;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.*;

@Service
public class FullBikeService {

    private static Logger LOGGER = LogManager.getLogger(FullBikeService.class);

    @Autowired
    private FullBikeDao fullBikeDao;

    public List<FullBike> getAllFullBikes() {
        List<FullBike> bikeList = fullBikeDao.findAll();
        LOGGER.info("Getting list of all bikes, number returned: " + bikeList.size());
        return bikeList;
    }

    public void create(FullBike bike) {
        LOGGER.info(("Adding new bike to DB!"));
        fullBikeDao.save(bike);
    }

    public List<String> getAllGroupsetBrandNames() {
        List<GroupsetBrand> groupsets = Arrays.stream(GroupsetBrand.values()).collect(Collectors.toList());
        List<String> names = new ArrayList<>();
        for (GroupsetBrand gs : groupsets) {
            names.add(gs.getName());
        }
        LOGGER.info("Getting all Groupset Brands available, number returned: " + names.size());
        return names;
    }

    public List<String> getAllFrameStyleNames() {
        List<FrameStyle> frameStyles = Arrays.stream(FrameStyle.values()).collect(Collectors.toList());
        List<String> names = new ArrayList<>();
        for (FrameStyle fs : frameStyles) {
            names.add(fs.getName());
        }
        LOGGER.info("Getting all Frame Styles available, number returned: " + names.size());
        return names;
    }

    public List<String> getAllBars() {
        List<HandleBarType> barTypes = Arrays.stream(HandleBarType.values()).collect(Collectors.toList());
        List<String> bars = new ArrayList<>();
        for (HandleBarType b : barTypes) {
            bars.add(b.getName());
        }
        LOGGER.info("Getting all Handlebar Types, number returned: " + bars.size());
        return bars;
    }

    public List<String> getAllBrakes() {
        List<BrakeType> brakeTypes = Arrays.stream(BrakeType.values()).collect(Collectors.toList());
        List<String> brakes = new ArrayList<>();
        for (BrakeType b : brakeTypes) {
            brakes.add(b.getName());
        }
        LOGGER.info("Getting all Brake Types, number returned: " + brakes.size());
        return brakes;
    }

    public FullBike updateBike(FullBike bike) {
        LOGGER.info("Updating bike in DB!");
        checkBikeShifters(bike);
        checkFrameStyle(bike);
        checkBrakeCompatibility(bike);
        fullBikeDao.save(bike);
        return bike;
    }

    private void checkBrakeCompatibility(FullBike bike) {
        if (bike.getBrakeType().equals(RIM) || bike.getBrakeType().equals(NOT_REQUIRED)) {
            bike.getFrame().setDiscBrakeCompatible(false);
        } else {
            bike.getFrame().setDiscBrakeCompatible(true);
        }
    }

    private void checkFrameStyle(FullBike bike) {
        if (bike.getFrame().getFrameStyle().equals(SINGLE_SPEED)) {
            bike.getFrame().setRequiresFrontGearCable(false);
            bike.getFrame().setRequiresRearGearCable(false);
        } else if (bike.getFrontGears().getNumberOfGears() > 1) {
            bike.getFrame().setRequiresFrontGearCable(true);
        } else if (bike.getRearGears().getNumberOfGears() > 1) {
            bike.getFrame().setRequiresFrontGearCable(true);
        } else {
            bike.getFrame().setRequiresFrontGearCable(true);
            bike.getFrame().setRequiresRearGearCable(true);
        }
    }

    private void checkBikeShifters(FullBike bike) {
        if (bike.getFrame().isRequiresFrontGearCable() || bike.getFrame().isRequiresRearGearCable()) {
            if (bike.getHandleBarType().equals(DROPS) || bike.getHandleBarType().equals(FLARE)) {
                bike.setShifterStyle(STI);
            } else if (bike.getHandleBarType().equals(FLAT)) {
                bike.setShifterStyle(TRIGGER);
            }
        } else {
            bike.setShifterStyle(NONE);
        }
    }

    public FullBike startNewBike() {
        LOGGER.info("Starting new bike, service method.");
        FullBike bike;
        List<FullBike> bikesOnDB = getBikeByName("Your Custom Bike");
        if (bikesOnDB.size() > 0) {
            LOGGER.info("Bike with that name already exists on DB.");
            bike = bikesOnDB.get(0);
        } else {
            Frame frame = new Frame();
            frame.setFrameStyle(FrameStyle.NONE_SELECTED);
            bike = new FullBike();
            bike.setBikeName("Your Custom Bike");
            bike.setFrame(frame);
            bike.setBrakeType(BrakeType.NOT_REQUIRED);
            bike.setShifterStyle(ShifterStyle.NONE);
            bike.setGroupsetBrand(GroupsetBrand.OTHER);
            bike.setHandleBarType(NOT_SELECTED);
            create(bike);
        }
        return bike;
    }

    private List<FullBike> getBikeByName(String bikeName) {
        List<FullBike> bikeList = fullBikeDao.findAll().stream().filter(item -> item.getBikeName().equals(bikeName)).collect(Collectors.toList());
        return bikeList;
    }
}