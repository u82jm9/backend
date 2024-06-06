package com.homeapp.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.backend.models.bike.BikeParts;
import com.homeapp.backend.models.bike.Error;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.homeapp.backend.models.bike.Enums.BrakeType.*;
import static com.homeapp.backend.models.bike.Enums.ShifterStyle.STI;

/**
 * The type Shimano groupset service.
 */
@Service
public class ShimanoGroupsetService {
    private static final String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static final String wiggleURL = "https://www.wiggle.com/p/";
    private static FullBike bike;
    private BikeParts bikeParts;
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ErrorLogger errorLogger = new ErrorLogger();
    private final FullBikeService fullBikeService;
    private static final String LINKS_FILE = "src/main/resources/links.json";
    private final ObjectMapper om;

    /**
     * Instantiates a new Shimano Groupset Service.
     * Autowires in a FullBike Service to allow for access to instance Bike in all methods within this class.
     *
     * @param fullBikeService the full bike service
     */
    @Autowired
    public ShimanoGroupsetService(@Lazy FullBikeService fullBikeService, ObjectMapper om) {
        this.fullBikeService = fullBikeService;
        this.om = om;
    }

    /**
     * Gets shimano groupset.
     * Sets the passed-in parts to the instance object, to be used throughout the class.
     * Runs each separate component method in parallel to improve efficiency.
     * Each component method chooses the correct link based on design bike and then retrieves the individual part information from the web.
     *
     * @param parts the parts
     */
    public void getShimanoGroupset(BikeParts parts) {
        infoLogger.log("Getting Parts for Shimano Groupset.");
        bikeParts = parts;
        bike = fullBikeService.getBike();
        if (!bike.getShifterStyle().equals(STI)) {
            getLeverShifters();
            getBrakeLevers();
        } else {
            if ((bike.getBrakeType().equals(MECHANICAL_DISC)) || (bike.getBrakeType().equals(RIM))) {
                getMechanicalSTIShifters();
            } else if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                getHydraulicSTIShifters();
            }
        }
        CompletableFuture<Void> brakeFuture = CompletableFuture.runAsync(this::getBrakeCalipers);
        CompletableFuture<Void> chainringFuture = CompletableFuture.runAsync(this::getChainring);
        CompletableFuture<Void> cassetteFuture = CompletableFuture.runAsync(this::getCassette);
        CompletableFuture<Void> chainFuture = CompletableFuture.runAsync(this::getChain);
        CompletableFuture<Void> rearDerailleurFuture = CompletableFuture.runAsync(this::getRearDerailleur);
        CompletableFuture<Void> frontDerailleurFuture = CompletableFuture.runAsync(this::getFrontDerailleur);
        CompletableFuture.allOf(brakeFuture, chainringFuture, cassetteFuture, chainFuture, rearDerailleurFuture, frontDerailleurFuture).join();
        if (!bikeParts.getErrorMessages().isEmpty()) {
            errorLogger.log("BikeParts has " + bikeParts.getErrorMessages().size() + " errors: " + bikeParts.getErrorMessages());
        }
    }

    private void getBrakeLevers() {
        String ref = "";
        String component = "Brake-Levers";
        String method = "getBrakeLevers";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
            ref = wiggleURL + "shimano-m6100-brake-2-piston-704288#colcode=70428803";
            findPartFromInternalRef("Left-" + ref);
            ref = wiggleURL + "shimano-m6100-brake-2-piston-704288#colcode=70428803";
            findPartFromInternalRef("Right-" + ref);
        } else {
            ref = wiggleURL + "shimano-deore-t610-v-brake-levers-930835#colcode=93083503";
            findPartFromInternalRef(ref);
        }
    }

    private void getBrakeCalipers() {
        String ref = "";
        String component = "Brake-Caliper";
        String method = "getBrakeCalipers";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch (bike.getBrakeType()) {
            case RIM -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = wiggleURL + "shimano-tiagra-r451-dual-pivot-brake-caliper-930477#colcode=93047703";
                } else {
                    ref = wiggleURL + "shimano-105-r7000-road-brake-caliper-932489#colcode=93248903";
                }
            }
            case MECHANICAL_DISC -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = wiggleURL + "shimano-br-r317-rear-road-disc-brake-caliper-930854#colcode=93085403";
                } else {
                    ref = chainReactionURL + "trp-spyre-post-mount-caliper-837329#colcode=83732903";
                }
            }
            default -> {
                warnLogger.log("Not getting link for calipers as Hydraulic calipers and levers are together");
            }
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef("Front-" + ref);
            findPartFromInternalRef("Rear-" + ref);
        } else {
            if (!bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                bikeParts.getErrorMessages().add(new Error(component, method, ref));
            }
        }
    }

    private void getMechanicalSTIShifters() {
        String ref = "";
        String component = "STI-Shifter";
        String method = "getMechanicalSTIShifters";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfFrontGears()) {
            //Could not find active site for 1 by components
            //Below links are useless, have taken out option for Frontend selection
            case 1 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = chainReactionURL + "microshift-advent-m090-1x9-speed-gear-brake-levers";
                } else if (bike.getNumberOfRearGears() == 10) {
                    ref = chainReactionURL + "shimano-tiagra-4700-sti-shifter-set-2x10";
                } else if (bike.getNumberOfRearGears() == 11) {
                    ref = wiggleURL + "shimano-105-r7000-11-speed-levers";
                } else {
                    ref = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-shifter-set";
                }
            }
            case 2 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = chainReactionURL + "shimano-sora-r3000-sti-mechanical-9-speed-shifter---for-double-912434#colcode=91243403";
                } else if (bike.getNumberOfRearGears() == 10) {
                    ref = wiggleURL + "shimano-tiagra-4700-double-sti-shifter-911771#colcode=91177103";
                } else
//                        if (bike.getNumberOfRearGears() == 11)
                {
                    ref = chainReactionURL + "shimano-105-r7000-mechanical-shifters--pair-913387#colcode=91338703";
                }
                //no website found for this option
//                    else {
//                        link = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-shifter-set";
//                    }
            }
            case 3 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = chainReactionURL + "microshift-r9-3x9-speed-dual-control-levers";
                } else {
                    ref = wiggleURL + "shimano-tiagra-4700-3x10-speed-lever-set";
                    bike.setNumberOfRearGears(10);
                    warnLogger.log("3 by Shimano Gears are restricted to a maximum of 10 at the back");
                }
            }
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getHydraulicSTIShifters() {
        String ref = "";
        String component = "Hydraulic-Shifter";
        String method = "getHydraulicSTIShifters";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        if (bike.getNumberOfRearGears() == 10) {
            ref = chainReactionURL + "shimano-tiagra-4725-2x10-speed-road-disc-brake";
        } else if (bike.getNumberOfRearGears() == 11) {
            ref = chainReactionURL + "shimano-105-r7025-hydraulic-disc-brake";
        } else if (bike.getNumberOfRearGears() == 12) {
            ref = wiggleURL + "shimano-105-r7170-di2-hydraulic-disc-brake";
        } else {
            ref = wiggleURL + "clarks-m2-hydraulic-disc-brake-with-rotor";
        }
        findPartFromInternalRef("Right-" + ref);
        if (bike.getNumberOfFrontGears() == 1) {
            ref = chainReactionURL + "shimano-grx-820-hydraulic-drop-bar-brake-lever";
        }
        findPartFromInternalRef("Left-" + ref);
    }

    private void getLeverShifters() {
        String ref = "";
        String component = "Trigger-Shifter";
        String method = "getLeverShifters";
        infoLogger.log("Getting Parts for: " + component);
        switch ((int) bike.getNumberOfRearGears()) {
            case 10 -> ref = wiggleURL + "shimano-deore-m6000-10-speed-trigger-shifter";
            case 11 -> ref = wiggleURL + "shimano-xt-m8000-11-speed-trigger-shifter";
            default -> ref = chainReactionURL + "shimano-altus-m2010-9-speed-shifter";
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getChainring() {
        String ref = "";
        String component = "Chainring";
        String method = "getChainring";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfFrontGears()) {
            //Could not find active site for 1 by components
            //Below links are useless, have taken out option for Frontend selection
            case 1 -> {
                if (bike.getNumberOfRearGears() == 10 || bike.getNumberOfRearGears() == 11) {
                    ref = chainReactionURL + "shimano-m5100-deore-10-11-speed-single-chainset";
                } else if (bike.getNumberOfRearGears() == 12) {
                    ref = chainReactionURL + "shimano-m6100-deore-12-speed-mtb-single-chainset";
                } else {
                    ref = wiggleURL + "miche-primato-advanced-track-single-chainset";
                }
            }
            case 2 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = chainReactionURL + "shimano-claris-r2000-8-speed-double-chainset";
                } else if (bike.getNumberOfRearGears() == 10) {
                    ref = chainReactionURL + "shimano-tiagra-4700-10-speed-chainset";
                } else if (bike.getNumberOfRearGears() == 11) {
                    ref = chainReactionURL + "shimano-105-r7000-11-speed-road-double-chainset";
                } else if (bike.getNumberOfRearGears() == 12) {
                    ref = chainReactionURL + "shimano-105-r7100-12-speed-double-chainset";
                }
            }
            case 3 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = wiggleURL + "shimano-sora-r3030-9-speed-triple-chainset";
                } else {
                    ref = chainReactionURL + "shimano-tiagra-4703-10sp-road-triple-chainset";
                    bike.setNumberOfRearGears(10);
                }
            }
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getCassette() {
        String ref = "";
        String component = "Cassette";
        String method = "getCassette";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfRearGears()) {
            case 9 -> ref = wiggleURL + "shimano-sora-hg400-9-speed-cassette";
            case 10 -> ref = chainReactionURL + "shimano-tiagra-hg500-10-speed-road-cassette-5360107149";
            case 11 -> ref = chainReactionURL + "shimano-105-r7000-11-speed-cassette";
            case 12 -> ref = chainReactionURL + "shimano-105-r7100-12-speed-cassette";
            default -> ref = wiggleURL + "shimano-dx-single-speed-sprocket";
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getChain() {
        String ref = "";
        String component = "Chain";
        String method = "getChain";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfRearGears()) {
            case 9 -> ref = wiggleURL + "shimano-xt-hg93-9-speed-chain";
            case 10 -> ref = wiggleURL + "shimano-hg95-10-speed-chain";
            case 11 -> ref = wiggleURL + "shimano-hg601q-105-5800-11-speed-chain";
            case 12 -> ref = wiggleURL + "shimano-slx-m7100-12-speed-chain";
            default -> ref = chainReactionURL + "shimano-nexus-single-speed-chain";
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getRearDerailleur() {
        String ref = "";
        String component = "Rear-Derailleur";
        String method = "getRearDerailleur";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfRearGears()) {
            case 9 -> ref = wiggleURL + "shimano-sora-r3000-9-speed-rear-derailleur";
            case 10 -> ref = wiggleURL + "shimano-tiagra-4700-10-speed-rear-derailleur-gs";
            case 11 -> ref = chainReactionURL + "shimano-105-r7000-11-speed-rear-derailleur";
            case 12 -> ref = chainReactionURL + "shimano-ultegra-r8150-di2-12-speed-rear-derailleur";
            default -> {
            }
        }
        if (!ref.isEmpty() && bike.getNumberOfRearGears() > 1) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    private void getFrontDerailleur() {
        String ref = "";
        String component = "Front-Derailleur";
        String method = "getFrontDerailleur";
        infoLogger.log("Getting Parts for: " + component);
        bike = fullBikeService.getBike();
        switch ((int) bike.getNumberOfFrontGears()) {
            case 1 -> {
                ref = wiggleURL + "deda-dog-fang-chain-catcher";
                warnLogger.log("Front Derailleur not required, providing chain catcher");
            }
            case 2 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = wiggleURL + "shimano-sora-r3000-9-speed-double-front-derailleur";
                } else if (bike.getNumberOfRearGears() == 10) {
                    ref = wiggleURL + "shimano-tiagra-fd4700-10-speed-front-derailleur";
                } else if (bike.getNumberOfRearGears() == 11) {
                    ref = wiggleURL + "shimano-105-r7000-11-speed-front-derailleur";
                } else if (bike.getNumberOfRearGears() == 12) {
                    ref = wiggleURL + "shimano-105-r7150-di2-e-tube-front-derailleur";
                }
            }
            case 3 -> {
                if (bike.getNumberOfRearGears() == 9) {
                    ref = wiggleURL + "shimano-sora-r3030-9-speed-triple-front-derailleur";
                } else {
                    ref = wiggleURL + "shimano-tiagra-4703-3x10sp-braze-on-front-mech";
                }
            }
        }
        if (!ref.isEmpty()) {
            findPartFromInternalRef(ref);
        } else {
            bikeParts.getErrorMessages().add(new Error(component, method, ref));
        }
    }

    public void findPartFromInternalRef(String internalRef) {
        infoLogger.log("Reading all Links from File");
        try {
            Optional<Part> part = retrievePartFromLinks(internalRef);
            part.ifPresentOrElse(p -> {
                        bikeParts.getListOfParts().add(p);
                        infoLogger.log("Part found and added to bikeParts: " + p);
                    },
                    () -> errorLogger.log("No Part was found on File for Internal Ref: " + internalRef));
        } catch (IOException e) {
            errorLogger.log("An IOException occurred from method: readLinksFile!!See error message: " + e.getMessage() + "!!From: " + getClass());
        }
    }

    private Optional<Part> retrievePartFromLinks(String ref) throws IOException {
        LinkedList<Part> parts = om.readValue(new File(LINKS_FILE), new TypeReference<>() {
        });
        return parts.stream().filter(p -> p.getLink().equals(ref)).findFirst();
    }
}