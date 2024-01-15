package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.Error;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;

@Service
public class ShimanoGroupsetService {

    private static final Logger LOGGER = LogManager.getLogger(ShimanoGroupsetService.class);
    private static final String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static final String wiggleURL = "https://www.wiggle.com/p/";
    private static FullBike bike;
    private BikeParts bikeParts;

    @Autowired
    FullBikeService fullBikeService;

    public void getShimanoGroupset(BikeParts parts) {
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
    }

    private void getBrakeLevers() {
        String link = "";
        String component = "Brake-Levers";
        try {
            bike = fullBikeService.getBike();
            if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                link = wiggleURL + "shimano-grx-812-sub-brake-lever";
                setBikePartsFromLink(link, "Left " + component);
                setBikePartsFromLink(link, "Right " + component);
            } else {
                link = wiggleURL + "shimano-deore-t610-v-brake-levers";
                setBikePartsFromLink(link, component);
            }
        } catch (IOException e) {
            handleIOException(component, "Get Brake Levers", e);
        }
    }

    private void getBrakeCalipers() {
        String link = "";
        String component = "Brake-Caliper";
        try {
            bike = fullBikeService.getBike();
            switch (bike.getBrakeType()) {
                case RIM -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3000-brake-caliper";
                    } else if (bike.getNumberOfRearGears() == 10) {
                        link = wiggleURL + "shimano-tiagra-4700-rear-brake-caliper";
                    } else {
                        link = chainReactionURL + "shimano-105-r7000-brake-caliper";
                    }
                }
                case MECHANICAL_DISC -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = wiggleURL + "shimano-sora-r317-mechanical-disc-brake-caliper";
                    } else {
                        link = wiggleURL + "trp-spyke-mechanical-disc-brake-caliper";
                    }
                }
                default -> {
                }
            }
            if (!link.isEmpty()) {
                setBikePartsFromLink(link, "Front " + component);
                setBikePartsFromLink(link, "Rear " + component);

            }
        } catch (IOException e) {
            handleIOException(component, "Get Brake calipers", e);
        }
    }

    private void getMechanicalSTIShifters() {
        String link = "";
        String component = "STI-Shifter";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfFrontGears()) {
                case 1 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = chainReactionURL + "microshift-r490-1x9-speed-dual-control-lever-set";
                    } else if (bike.getNumberOfRearGears() == 10) {
                        link = chainReactionURL + "shimano-tiagra-4700-sti-shifter-set-2x10";
                    } else if (bike.getNumberOfRearGears() == 11) {
                        link = wiggleURL + "shimano-105-r7000-11-speed-levers";
                    } else {
                        link = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-shifter-set";
                    }
                }
                case 2 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = chainReactionURL + "shimano-sora-r3000-2x9-speed-gear-brake-levers";
                    } else if (bike.getNumberOfRearGears() == 10) {
                        link = chainReactionURL + "shimano-tiagra-4700-sti-shifter-set-2x10";
                    } else if (bike.getNumberOfRearGears() == 11) {
                        link = wiggleURL + "shimano-105-r7000-11-speed-levers";
                    } else {
                        link = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-shifter-set";
                    }
                }
                case 3 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = chainReactionURL + "microshift-r9-3x9-speed-dual-control-levers";
                    } else {
                        link = wiggleURL + "shimano-tiagra-4700-3x10-speed-lever-set";
                        bike.setNumberOfRearGears(10);
                        LOGGER.warn("3 by Shimano Gears are restricted to a maximum of 10 at the back");
                    }
                }
            }
            setBikePartsFromLink(link, component);
        } catch (IOException e) {
            handleIOException(component, "Get STI Shifters", e);
        }
    }

    private void getHydraulicSTIShifters() {
        String link = "";
        String component = "Hydraulic-Shifter";
        try {
            bike = fullBikeService.getBike();
            if (bike.getNumberOfRearGears() == 10) {
                link = chainReactionURL + "shimano-tiagra-4725-2x10-speed-road-disc-brake";
            } else if (bike.getNumberOfRearGears() == 11) {
                link = chainReactionURL + "shimano-105-r7025-hydraulic-disc-brake";
            } else if (bike.getNumberOfRearGears() == 12) {
                link = wiggleURL + "shimano-105-r7170-di2-hydraulic-disc-brake";
            } else {
                link = wiggleURL + "clarks-m2-hydraulic-disc-brake-with-rotor";
            }
            setBikePartsFromLink(link, "Right " + component);
            if (bike.getNumberOfFrontGears() == 1) {
                link = chainReactionURL + "shimano-grx-820-hydraulic-drop-bar-brake-lever";
            }
            setBikePartsFromLink(link, "Left " + component);
        } catch (
                IOException e) {
            handleIOException(component, "Get Hydraulic Shifters", e);
        }
    }

    private void getLeverShifters() {
        String link = "";
        String component = "Trigger-Shifter";
        try {
            switch ((int) bike.getNumberOfRearGears()) {
                case 10 -> link = wiggleURL + "shimano-deore-m6000-10-speed-trigger-shifter";
                case 11 -> link = wiggleURL + "shimano-xt-m8000-11-speed-trigger-shifter";
                default -> {
                }
            }
            if (!link.isEmpty()) {
                setBikePartsFromLink(link, component);
            }
        } catch (IOException e) {
            handleIOException(component, "Get Trigger shifters", e);
        }
    }

    private void getChainring() {
        String link = "";
        String component = "Chainring";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfFrontGears()) {
                case 1 -> {
                    if (bike.getNumberOfRearGears() == 10 || bike.getNumberOfRearGears() == 11) {
                        link = chainReactionURL + "shimano-m5100-deore-10-11-speed-single-chainset";
                    } else if (bike.getNumberOfRearGears() == 12) {
                        link = chainReactionURL + "shimano-m6100-deore-12-speed-mtb-single-chainset";
                    } else {
                        link = wiggleURL + "miche-primato-advanced-track-chainset-5360048572";
                    }
                }
                case 2 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = chainReactionURL + "shimano-claris-r2000-8-speed-double-chainset";
                    } else if (bike.getNumberOfRearGears() == 10) {
                        link = chainReactionURL + "shimano-tiagra-4700-10-speed-chainset";
                    } else if (bike.getNumberOfRearGears() == 11) {
                        link = chainReactionURL + "shimano-105-r7000-11-speed-road-double-chainset";
                    } else if (bike.getNumberOfRearGears() == 12) {
                        link = chainReactionURL + "shimano-105-r7100-12-speed-double-chainset";
                    }
                }
                case 3 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3030-9-speed-triple-chainset";
                    } else {
                        link = chainReactionURL + "shimano-tiagra-4703-10sp-road-triple-chainset";
                        bike.setNumberOfRearGears(10);
                    }
                }
            }
            setBikePartsFromLink(link, component);
        } catch (IOException e) {
            handleIOException(component, "Get Chainring", e);
        }
    }

    private void getCassette() {
        String link = "";
        String component = "Cassette";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfRearGears()) {
                case 1 -> link = wiggleURL + "shimano-dx-single-speed-sprocket";
                case 9 -> link = wiggleURL + "shimano-sora-hg400-9-speed-cassette";
                case 10 -> link = chainReactionURL + "shimano-tiagra-hg500-10-speed-road-cassette-5360107149";
                case 11 -> link = chainReactionURL + "shimano-105-r7000-11-speed-cassette";
                case 12 -> link = chainReactionURL + "shimano-105-r7100-12-speed-cassette";
                default -> {
                }
            }
            if (!link.isEmpty()) {
                setBikePartsFromLink(link, component);
            }
        } catch (IOException e) {
            handleIOException(component, "Get Cassette", e);
        }
    }

    private void getChain() {
        String link = "";
        String component = "Chain";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfRearGears()) {
                case 9 -> link = wiggleURL + "shimano-xt-hg93-9-speed-chain";
                case 10 -> link = wiggleURL + "shimano-hg95-10-speed-chain";
                case 11 -> link = wiggleURL + "shimano-hg601q-105-5800-11-speed-chain";
                case 12 -> link = wiggleURL + "shimano-slx-m7100-12-speed-chain";
                default -> link = chainReactionURL + "shimano-nexus-single-speed-chain";
            }
            setBikePartsFromLink(link, component);
        } catch (IOException e) {
            handleIOException(component, "Get Chain", e);
        }
    }

    private void getRearDerailleur() {
        String link = "";
        String component = "Rear-Derailleur";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfRearGears()) {
                case 9 -> link = wiggleURL + "shimano-sora-r3000-9-speed-rear-derailleur";
                case 10 -> link = wiggleURL + "shimano-tiagra-4700-10-speed-rear-derailleur-gs";
                case 11 -> link = chainReactionURL + "shimano-105-r7000-11-speed-rear-derailleur";
                case 12 -> link = chainReactionURL + "shimano-ultegra-r8150-di2-12-speed-rear-derailleur";
                default -> {
                }
            }
            if (!link.isEmpty()) {
                setBikePartsFromLink(link, component);
            }
        } catch (IOException e) {
            handleIOException(component, "Get Rear Derailleur", e);
        }
    }

    private void getFrontDerailleur() {
        String link = "";
        String component = "Front-Derailleur";
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getNumberOfFrontGears()) {
                case 1 -> {
                    link = wiggleURL + "deda-dog-fang-chain-catcher";
                    LOGGER.info("Front Derailleur not required, providing chain catcher");
                }
                case 2 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3000-9-speed-double-front-derailleur";
                    } else if (bike.getNumberOfRearGears() == 10) {
                        link = wiggleURL + "shimano-tiagra-fd4700-10-speed-front-derailleur";
                    } else if (bike.getNumberOfRearGears() == 11) {
                        link = wiggleURL + "shimano-105-r7000-11-speed-front-derailleur";
                    } else if (bike.getNumberOfRearGears() == 12) {
                        link = wiggleURL + "shimano-105-r7150-di2-e-tube-front-derailleur";
                    }
                }
                case 3 -> {
                    if (bike.getNumberOfRearGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3030-9-speed-triple-front-derailleur";
                    } else {
                        link = wiggleURL + "shimano-tiagra-4703-3x10sp-braze-on-front-mech";
                    }
                }
            }
            setBikePartsFromLink(link, component);
        } catch (IOException e) {
            handleIOException(component, "Get Front Derailleur", e);
        }
    }

    public void setBikePartsFromLink(String link, String component) throws IOException {
        try {
            bike = fullBikeService.getBike();
            LOGGER.info("Connecting to link: {}", link);
            LOGGER.info("For Component: {}", component);
            Document doc = Jsoup.connect(link).get();
            Optional<Element> e = Optional.of(doc.select("div.ProductDetail_container__FX6xF").get(0));
            if (e.isEmpty()) {
                handleError(component, "SetBikePartsFromLink", link);
            } else {
                String name = e.get().select("h1").first().text();
                String price = e.get().select("div.ProductPrice_productPrice__Fg1nA")
                        .select("p").first().text().replace("£", "").split(" ")[0];
                if (!price.contains(".")) {
                    price = price + ".00";
                }
                LOGGER.info("Found Product: " + name);
                LOGGER.info("For Price: " + price);
                LOGGER.info("Link: " + link);
                bikeParts.getListOfParts().add(new Part(component, name, price, link));
            }
        } catch (IOException e) {
            handleIOException(component, "SetBikePartsFromLink", e);
        }
    }

    public void handleError(String component, String method, String link) {
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, link));
        bikeParts.setErrorMessages(tempList);
        LOGGER.error("An Error occurred from: {}!\nConnecting to link: {}\nFor bike Component: {}", method, link, component);
    }

    public void handleIOException(String component, String method, IOException e) {
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
        LOGGER.error("An IOException occurred from: {}!\nConnecting to link: {}\nFor bike Component: {}", method, e.getMessage(), component);
    }

    public void handleIOException(String component, String method, Exception e) {
        List<Error> tempList = bikeParts.getErrorMessages();
        tempList.add(new Error(component, method, e.getMessage()));
        bikeParts.setErrorMessages(tempList);
        LOGGER.error("An Exception occurred from: {}!\nConnecting to link: {}\nFor bike Component: {}", method, e.getMessage(), component);
    }
}