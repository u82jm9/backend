package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.bike.Enums.*;
import com.homeapp.one.demo.models.bike.Frame;
import com.homeapp.one.demo.models.bike.FrontGears;
import com.homeapp.one.demo.models.bike.FullBike;
import com.homeapp.one.demo.models.bike.RearGears;
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
import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.*;
import static com.homeapp.one.demo.models.bike.Enums.HandleBarType.*;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.*;

@Service
public class FullBikeService {

    private static Logger LOGGER = LogManager.getLogger(FullBikeService.class);
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

    public List<String> getShimanoGroupset() {
        List<ShimanoGroupSet> sg = Arrays.stream(ShimanoGroupSet.values()).collect(Collectors.toList());
        List<String> groupsets = new ArrayList<>();
        for (ShimanoGroupSet b : sg) {
            groupsets.add(b.getName());
        }
        LOGGER.info("Getting all Shimano Groupsets, number returned: " + groupsets.size());
        return groupsets;
    }

    public List<String> getSramGroupset() {
        List<SramGroupSet> sg = Arrays.stream(SramGroupSet.values()).collect(Collectors.toList());
        List<String> groupsets = new ArrayList<>();
        for (SramGroupSet b : sg) {
            groupsets.add(b.getName());
        }
        LOGGER.info("Getting all Sram Groupsets, number returned: " + groupsets.size());
        return groupsets;
    }

    public List<String> getCampagGroupset() {
        List<CampagnoloGroupSet> cg = Arrays.stream(CampagnoloGroupSet.values()).collect(Collectors.toList());
        List<String> groupsets = new ArrayList<>();
        for (CampagnoloGroupSet b : cg) {
            groupsets.add(b.getName());
        }
        LOGGER.info("Getting all Campagnolo Groupsets, number returned: " + groupsets.size());
        return groupsets;
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
            bike.getRearGears().setNumberOfGears(1);
            bike.getFrontGears().setNumberOfGears(1);
        } else if (bike.getFrame().getFrameStyle().equals(GRAVEL)) {
            bike.getFrame().setDiscBrakeCompatible(true);
            bike.getFrame().setRequiresFrontGearCable(false);
        } else if (bike.getFrame().getFrameStyle().equals(TOUR)) {
            bike.getFrame().setDiscBrakeCompatible(true);
        }
        if (bike.getFrontGears().getNumberOfGears() > 1) {
            bike.getFrame().setRequiresFrontGearCable(true);
        } else {
            bike.getFrame().setRequiresFrontGearCable(false);
        }
        if (bike.getRearGears().getNumberOfGears() > 1) {
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
            frame.setFrameStyle(FrameStyle.NONE_SELECTED);
            bike = new FullBike();
            bike.setBikeName("Your Custom Bike");
            bike.setFrame(frame);
            bike.setBrakeType(BrakeType.NOT_REQUIRED);
            bike.setShifterStyle(ShifterStyle.NONE);
            bike.setGroupsetBrand(GroupsetBrand.OTHER);
            bike.setHandleBarType(NOT_SELECTED);
            bike.setFrontGears(new FrontGears(1));
            bike.setRearGears(new RearGears(1));
            create(bike);
        }
        return bike;
    }

    private List<FullBike> getBikesByName(String bikeName) {
        LOGGER.info("Getting List of Bikes with bike name: " + bikeName);
        List<FullBike> bikeList = getAllFullBikes().stream().filter(item -> item.getBikeName().equals(bikeName)).collect(Collectors.toList());
        return bikeList;
    }

    public FullBike getBikeUsingName(String bikeName) {
        LOGGER.info("Getting single bike with bike name: " + bikeName);
        FullBike bike = getAllFullBikes().stream().filter(item -> item.getBikeName().equals(bikeName)).collect(Collectors.toList()).get(0);
        return bike;
    }
}