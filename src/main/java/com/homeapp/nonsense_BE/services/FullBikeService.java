package com.homeapp.nonsense_BE.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.Exceptions.ExceptionHandler;
import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.*;

@Service
public class FullBikeService {

    private static FullBikeService instance;
    private FullBike bike;
    private static final ObjectMapper om = new ObjectMapper();
    private static final String JSON_BIKES_FILE = "src/main/resources/bikes.json";
    private static final String JSON_BIKES_FILE_BACKUP = "src/main/resources/bikes_backup.json";
    private List<FullBike> bikeList;
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ShimanoGroupsetService shimanoGroupsetService;
    private final ExceptionHandler exceptionHandler;

    @Autowired
    public FullBikeService(@Lazy ShimanoGroupsetService shimanoGroupsetService, ExceptionHandler exceptionHandler) {
        this.shimanoGroupsetService = shimanoGroupsetService;
        this.exceptionHandler = exceptionHandler;
        this.bike = new FullBike();
        this.bikeList = readBikesFile();
    }

    public static FullBikeService getInstance(ShimanoGroupsetService shimanoGroupsetService, ExceptionHandler exceptionHandler) {
        if (instance == null) {
            instance = new FullBikeService(shimanoGroupsetService, exceptionHandler);
        }
        return instance;
    }

    private List<FullBike> readBikesFile() {
        infoLogger.log("Reading Bikes From File");
        try {
            File file = new File(JSON_BIKES_FILE);
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            exceptionHandler.handleIOException("Bike Service", "Read bikes from File", e);
        }
        return new ArrayList<>();
    }

    public void reloadBikesFromBackup() {
        infoLogger.log("Reloading Bikes From Backup File");
        try {
            deleteAllBikes();
            File file = new File(JSON_BIKES_FILE_BACKUP);
            List<FullBike> bikes = om.readValue(file, new TypeReference<>() {
            });
            writeBikesToFile(bikes);
        } catch (IOException e) {
            exceptionHandler.handleIOException("Bike Service", "Read bikes from Back up File", e);
        }
    }

    public void writeBikesToFile(List<FullBike> list) {
        infoLogger.log("Writing Bikes Back to File");
        try {
            om.writeValue(new File(JSON_BIKES_FILE), list);
            bikeList = list;
        } catch (IOException e) {
            exceptionHandler.handleIOException("Bike Service", "Write bikes to File", e);
        }
    }

    private List<FullBike> getBikeList() {
        return bikeList;
    }

    public FullBike getBike() {
        return bike;
    }

    public void setBike(FullBike bike) {
        this.bike = bike;
    }

    public List<FullBike> getAllFullBikes() {
        List<FullBike> bikeList = getBikeList();
        warnLogger.log("Getting list of all bikes, number returned: " + bikeList.size());
        return bikeList;
    }

    public void create(FullBike bike) {
        infoLogger.log("Adding new bike!");
        long newId = bikeList.size() + 1;
        bike.setFullBikeId(newId);
        bikeList.add(bike);
        writeBikesToFile(bikeList);
    }

    public FullBike updateBike(FullBike bike) {
        infoLogger.log("Updating bike on File!");
        setBike(bike);
        checkBikeShifters(bike);
        checkFrameStyle(bike);
        checkBrakeCompatibility(bike);
        if (getBikeUsingName(bike.getBikeName()).isPresent()) {
            removeBikeFromFile(bike.getBikeName());
        }
        bikeList.add(bike);
        writeBikesToFile(bikeList);
        return bike;
    }

    private void removeBikeFromFile(String bikeName) {
        bikeList.removeIf(i -> i.getBikeName().equals(bikeName));
        writeBikesToFile(bikeList);
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
        infoLogger.log("Starting new bike, service method.");
        Optional<FullBike> b = getBikeUsingName("Your Custom Bike");
        if (b.isPresent()) {
            warnLogger.log("Bike with that name already exists on DB.");
            return b.get();
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
            return bike;
        }
    }

    public Optional<FullBike> getBikeUsingName(String bikeName) {
        warnLogger.log("Getting single bike with bike name: " + bikeName);
        return bikeList.stream()
                .filter(item -> item.getBikeName().equals(bikeName))
                .findFirst();
    }

    public void deleteBike(long bikeId) {
        warnLogger.log("Deleting Bike with ID: " + bikeId);
        bikeList.removeIf(i -> i.getFullBikeId() == (bikeId));
        writeBikesToFile(bikeList);
    }

    public void deleteAllBikes() {
        infoLogger.log("Deleting ALL BIKES on File");
        warnLogger.log("Deleting ALL BIKES on File");
        writeBikesToFile(new ArrayList<>());
    }
}