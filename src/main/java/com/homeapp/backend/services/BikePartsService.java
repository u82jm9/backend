package com.homeapp.backend.services;

import com.homeapp.backend.models.bike.BikeParts;
import com.homeapp.backend.models.bike.Error;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static com.homeapp.backend.models.bike.Enums.BrakeType.RIM;
import static com.homeapp.backend.models.bike.Enums.FrameStyle.SINGLE_SPEED;
import static com.homeapp.backend.models.bike.Enums.GroupsetBrand.SHIMANO;

/**
 * The Bike Parts Service.
 * Houses all methods relating to getting Bike Parts for a given design Bike.
 */
@Service
@Scope("singleton")
public class BikePartsService {

    private static final String chainReactionURL = "https://www.chainreactioncycles.com/";
    private static final String wiggleURL = "https://www.wiggle.com/";
    private static final String haloURL = "https://www.halowheels.com/shop/wheels/";
    private static final String dolanURL = "https://www.dolan-bikes.com/";
    private static final String genesisURL = "https://www.genesisbikes.co.uk/";
    private static FullBike bike;
    private BikeParts bikeParts;
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ErrorLogger errorLogger = new ErrorLogger();
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
        String ref;
        bike = fullBikeService.getBike();
        infoLogger.log("Method for getting Bike Wheels from Web");
        if (!bike.getFrame().getFrameStyle().equals(SINGLE_SPEED)) {
            // Wheels which require Gears are from Wiggle
            if (!bike.getBrakeType().equals(RIM)) {
                if (bike.getWheelPreference().equals("Cheap")) {
                    ref = wiggleURL + "mavic-allroad-disc-650b-wheelset-845223#colcode=84522303";
                } else {
                    ref = wiggleURL + "deda-rs4-db-carbon-tubeless-wheels-836218#colcode=83621890";
                }
            } else {
                if (bike.getWheelPreference().equals("Cheap")) {
                    ref = wiggleURL + "miche-altur-wheels-846217#colcode=84621703";
                } else {
                    ref = wiggleURL + "miche-altur-wheels-846217#colcode=84621703";
                }
            }
        } else {
            // Wheels for Single Speed are from Halo
            if (bike.getWheelPreference().equals("Cheap")) {
                ref = haloURL + "aerorage-track-700c-wheels/";
            } else {
                ref = haloURL + "carbaura-crit-700c-wheelset/";
            }
        }
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
            bike = fullBikeService.getBike();
            infoLogger.log("Method for Getting Handlebar Parts from web");
            switch (bike.getHandleBarType()) {
                case DROPS -> ref = chainReactionURL + "thomson-alloy-road-drop-bar-aero-top-837837#colcode=83783703";
                case FLAT -> ref = chainReactionURL + "dmr-odub-handlebar-318mm-clamp-836711#colcode=83671103";
                case BULLHORNS -> ref = wiggleURL + "deda-crononero-low-rider-tri-bar-836815#colcode=83681503";
                case FLARE ->
                        ref = chainReactionURL + "thomson-carbon-dirt-drop-drop-bar-25d-flare-837830#colcode=83783003";
            }
            shimanoGroupsetService.findPartFromInternalRef(ref);
        } catch (Exception e) {
            bikeParts.getErrorMessages().add(new Error(component, method, e.getMessage()));
            errorLogger.log("An Exception occurred from: " + method + "!!See error message: " + e.getMessage() + "!!For bike Component: " + component);
        }
    }

    private void getFramePartsLink() {
        String ref = "";
        bike = fullBikeService.getBike();
        infoLogger.log("Method for Getting Frame Parts Link");
        switch (bike.getFrame().getFrameStyle()) {
            case ROAD -> {
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    ref = dolanURL + "dolan-adx-disc-titanium-road-frameset/";
                } else {
                    ref = dolanURL + "adx-titanium-road-frameset/";
                }
            }
            case TOUR -> {
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    ref = genesisURL + "genesis-fugio-frameset-vargn22330/";
                } else {
                    ref = genesisURL + "genesis-equilibrium-725-frameset-vargn21810";
                }
            }
            case GRAVEL -> {
                ref = dolanURL + "dolan-gxa2020-aluminium-gravel-frameset/";
            }
            case SINGLE_SPEED -> {
                ref = dolanURL + "dolan-pre-cursa-aluminium-frameset/";
            }
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
            p.setPrice(p.getPrice().replace(",", ""));
            BigDecimal bd = new BigDecimal(p.getPrice());
            bd = bd.setScale(2, RoundingMode.CEILING);
            total = total.add(bd);
        }
        bikeParts.setTotalBikePrice(total);
        bikeParts.setTotalPriceAsString(NumberFormat.getCurrencyInstance(Locale.UK).format(total));

    }
}