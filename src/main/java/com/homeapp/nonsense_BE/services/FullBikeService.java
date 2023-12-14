package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.repository.FullBikeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.*;

@Service
public class FullBikeService {

    private static final Logger LOGGER = LogManager.getLogger(FullBikeService.class);
    private static FullBikeService instance;
    private FullBike bike;

    @Autowired
    private FullBikeDao fullBikeDao;

    private FullBikeService() {
        this.bike = new FullBike();
    }

    public static FullBikeService getInstance() {
        if (instance == null) {
            instance = new FullBikeService();
        }
        return instance;
    }

    public FullBike getBike() {
        return bike;
    }

    public void setBike(FullBike bike) {
        this.bike = bike;
    }

    public List<FullBike> getAllFullBikes() {
        List<FullBike> bikeList = fullBikeDao.findAll();
        LOGGER.info("Getting list of all bikes, number returned: " + bikeList.size());
        return bikeList;
    }

    public void create(FullBike bike) {
        LOGGER.info(("Adding new bike to DB!"));
        fullBikeDao.save(bike);
    }

    public FullBike updateBike(FullBike bike) {
        LOGGER.info("Updating bike in DB!");
        setBike(bike);
        checkBikeShifters(bike);
        checkFrameStyle(bike);
        checkBrakeCompatibility(bike);
        fullBikeDao.save(bike);
        return getBike();
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
            bike.setNumberOfRearGears(1);
            bike.setNumberOfFrontGears(1);
        } else if (bike.getFrame().getFrameStyle().equals(GRAVEL)) {
            bike.getFrame().setDiscBrakeCompatible(true);
            bike.getFrame().setRequiresFrontGearCable(false);
        } else if (bike.getFrame().getFrameStyle().equals(TOUR)) {
            bike.getFrame().setDiscBrakeCompatible(true);
        }
        if (bike.getNumberOfFrontGears() > 1) {
            bike.getFrame().setRequiresFrontGearCable(true);
        } else {
            bike.getFrame().setRequiresFrontGearCable(false);
        }
        if (bike.getNumberOfRearGears() > 1) {
            bike.getFrame().setRequiresRearGearCable(true);
        } else {
            bike.getFrame().setRequiresRearGearCable(false);
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
        List<FullBike> bikesOnDB = getBikesByName("Your Custom Bike");
        if (bikesOnDB.size() > 0) {
            LOGGER.info("Bike with that name already exists on DB.");
            bike = bikesOnDB.get(0);
        } else {
            Frame frame = new Frame();
            frame.setFrameStyle(NONE_SELECTED);
            bike = new FullBike();
            bike.setBikeName("Your Custom Bike");
            bike.setFrame(frame);
            bike.setBrakeType(NO_SELECTION);
            bike.setShifterStyle(NONE);
            bike.setGroupsetBrand(SHIMANO);
            bike.setHandleBarType(NOT_SELECTED);
            bike.setNumberOfFrontGears(0);
            bike.setNumberOfRearGears(0);
            create(bike);
        }
        return bike;
    }

    private List<FullBike> getBikesByName(String bikeName) {
        LOGGER.info("Getting List of Bikes with bike name: " + bikeName);
        return getAllFullBikes().stream().filter(item -> item.getBikeName().equals(bikeName)).collect(Collectors.toList());
    }

    public FullBike getBikeUsingName(String bikeName) {
        LOGGER.info("Getting single bike with bike name: " + bikeName);
        return getAllFullBikes().stream().filter(item -> item.getBikeName().equals(bikeName)).collect(Collectors.toList()).get(0);
    }

    public void deleteBike(long bikeId) {

        LOGGER.info("Deleting Bike with ID: {}", bikeId);
        fullBikeDao.deleteById(bikeId);
    }

    public void deleteAllBikes() {
        LOGGER.info("Deleting ALL BIKES on DataBase");
        fullBikeDao.deleteAll();
    }

    private void handleIOException(String message, IOException e) {
        LOGGER.error("An IOException occurred from: " + message + "! " + e.getMessage());
    }
}