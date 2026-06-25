package com.homeapp.backend.services;

import com.homeapp.backend.models.bike.BikeParts;
import com.homeapp.backend.models.bike.Error;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static com.homeapp.backend.models.bike.Enums.BrakeType.RIM;
import static com.homeapp.backend.models.bike.Enums.FrameStyle.SINGLE_SPEED;
import static com.homeapp.backend.models.bike.Enums.GroupsetBrand.SHIMANO;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 * The Bike Parts Service.
 * Houses all methods relating to getting Bike Parts for a given design Bike.
 */
@Service
@Scope("singleton")
public class BikePartsService {

    private final static String LINKS_FILE = "src/main/resources/links.json";
    private final static String BACKUP_LINKS_FILE = "src/main/resources/links_backup.json";
    private static final ObjectMapper om = new ObjectMapper();
    private static FullBike bike;
    private BikeParts bikeParts;
    private final Logger logger = Logger.getLogger(BikePartsService.class.getName());
    private final FullBikeService fullBikeService;
    private final ShimanoGroupsetService shimanoGroupsetService;

    public BikePartsService() {
        this.fullBikeService = null;
        this.shimanoGroupsetService = null;
    }

    /**
     * Instantiates a new Bike parts service.
     * This instantiation is Autowired to allow this Service class to use methods from the other Service classes and the Exception Handler.
     *
     * @param fullBikeService        the Full Bike Service
     * @param shimanoGroupsetService the Shimano Groupset Service
     */
    @Autowired
    public BikePartsService(FullBikeService fullBikeService, ShimanoGroupsetService shimanoGroupsetService) {
        this.fullBikeService = fullBikeService;
        this.shimanoGroupsetService = shimanoGroupsetService;
        this.bikeParts = new BikeParts();
    }

    /**
     * Gets bike parts for bike, each call of this method uses a new BikeParts object, so has no influence from previous calls.
     * Bike parts Object is set to this instance. Each update to the Bike Parts object is done on this instance.
     * Method uses the bike that is currently on the instance of the Full Bike Service.
     * Sets of each individual get part methods, in parallel to save time, then combines the results into a single return Object.
     *
     * @return the Bike Parts Object
     */
    public BikeParts getBikePartsForBike() {
        bikeParts = new BikeParts();
        bike = fullBikeService.getBike();
        CompletableFuture<Void> handleBarFuture = CompletableFuture.runAsync(this::getHandlebarPartsLink);
        CompletableFuture<Void> frameFuture = CompletableFuture.runAsync(this::getFramePartsLink);
        CompletableFuture<Void> gearFuture = CompletableFuture.runAsync(this::getGearSetLink);
        CompletableFuture<Void> wheelFuture = CompletableFuture.runAsync(this::getWheelsLink);
        CompletableFuture.allOf(handleBarFuture, frameFuture, gearFuture, wheelFuture).join();
        calculateTotalPrice();
        return bikeParts;
    }

    private void getWheelsLink() {
        bike = fullBikeService.getBike();
        String ref = "";
        if (!Objects.equals(bike.getWheelPreference(), "Expensive") && !Objects.equals(bike.getWheelPreference(), "Cheap")) {
            bikeParts.getErrorMessages().add(new Error("Wheels", "GetWheelsLink", "Wheel Preference is not set to Cheap or Expensive"));
            logger.log(SEVERE, "An Error occurred from: GetWheelsLink!!\nWheel Preference is not set to Cheap or Expensive!!");
        } else {
            logger.log(INFO, "Method for getting Bike Wheels from Web");
            if (!bike.getFrame().getFrameStyle().equals(SINGLE_SPEED)) {
                // Wheels which require Gears are from Wiggle
                if (!bike.getBrakeType().equals(RIM)) {
                    if (bike.getWheelPreference().equals("Cheap")) {
                        ref = "WheelRimCheap";
                    } else {
                        ref = "WheelRimExpensive";
                    }
                } else {
                    if (bike.getWheelPreference().equals("Cheap")) {
                        ref = "WheelRimExpensive";
                    } else {
                        ref = "WheelDiscExpensive";
                    }
                }
            } else {
                // Wheels for Single Speed are from Halo
                if (bike.getWheelPreference().equals("Cheap")) {
                    ref = "WheelFixieCheap";
                } else {
                    ref = "WheelFixieExpensive";
                }
            }
        }
        assert shimanoGroupsetService != null;
        shimanoGroupsetService.findPartFromInternalRef(ref);
    }

    private void getGearSetLink() {
        bike = fullBikeService.getBike();
        bike.setGroupsetBrand(SHIMANO);
        shimanoGroupsetService.getShimanoGroupset(bikeParts);
    }

    private void getHandlebarPartsLink() {
        String ref = "";
        String component = "HandleBars";
        String method = "GetHandleBarParts";
        try {
            assert fullBikeService != null;
            bike = fullBikeService.getBike();
            logger.log(INFO, "Method for Getting Handlebar Parts from web");
            switch (bike.getHandleBarType()) {
                case DROPS -> ref = "BarsDrop";
                case FLAT -> ref = "BarsFlat";
                case BULLHORNS -> ref = "BarsBull";
                case FLARE -> ref = "BarsFlare";
            }
            shimanoGroupsetService.findPartFromInternalRef(ref);
        } catch (Exception e) {
            bikeParts.getErrorMessages().add(new Error(component, method, e.getMessage()));
            logger.log(SEVERE, "An Exception occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
        }
    }

    private void getFramePartsLink() {
        String ref = "";
        bike = fullBikeService.getBike();
        logger.log(INFO, "Method for Getting Frame Parts Link");
        switch (bike.getFrame().getFrameStyle()) {
            case ROAD -> {
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    ref = "FrameRoadDisc";
                } else {
                    ref = "FrameRoadRim";
                }
            }
            case TOUR -> {
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    ref = "FrameTourDisc";
                } else {
                    ref = "FrameTourRim";
                }
            }
            case GRAVEL -> ref = "FrameGravel";
            case SINGLE_SPEED -> ref = "FrameFixie";
        }
        shimanoGroupsetService.findPartFromInternalRef(ref);
    }

    /**
     * Takes the price of each part on the bike parts instance object and sums them to create a total price.
     * Restructures the big decimal value into a String for displaying on FE.
     */
    private void calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        for (Part p : bikeParts.getListOfParts()) {
            if (p.getPrice() != null) {
                p.setPrice(p.getPrice().replace(",", ""));
                BigDecimal bd = new BigDecimal(p.getPrice());
                bd = bd.setScale(2, RoundingMode.CEILING);
                total = total.add(bd);
            }
        }
        bikeParts.setTotalBikePrice(total);
        bikeParts.setTotalPriceAsString(NumberFormat.getCurrencyInstance(Locale.UK).format(total));

    }

    public void reloadLinksFromBackup() {
        logger.log(INFO, "Re-loading links from backup");
        try {
            List<Part> parts = om.readValue(new File(BACKUP_LINKS_FILE), new TypeReference<>() {
            });
            writeLinksToFile(parts);
        } catch (Exception e) {
            logger.log(SEVERE, "Error while reading backup links file");
        }
    }

    private void writeLinksToFile(List<Part> parts) {
        logger.log(INFO, "Writing backup links to file");
        try {
            om.writeValue(new File(LINKS_FILE), parts);
        } catch (Exception e) {
            logger.log(SEVERE, "Error while writing links to file");
        }
    }
}