package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.bike.FullBike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import static com.homeapp.one.demo.models.bike.Enums.BrakeType.HYDRAULIC_DISC;
import static com.homeapp.one.demo.models.bike.Enums.BrakeType.MECHANICAL_DISC;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.STI;
import static com.homeapp.one.demo.models.bike.Enums.ShimanoGroupSet.*;

@Service
public class ShimanoGroupsetService {

    private static Logger LOGGER = LogManager.getLogger(ShimanoGroupsetService.class);
    private static String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static String wiggleURL = "https://www.wiggle.com/p/";
    private static String link;
    private static FullBike bike;

    @Autowired
    FullBikeService fullBikeService;

    public void getShimanoGroupset() {
        getChainring();
        getCassette();
        getChain();
        getRearDerailleur();
        getFrontDerailleur();
        if (bike.getShifterStyle().equals(STI)) {
            getSTIShifters();
        } else {
            getLeverShifters();
        }
    }

    private void getChainring() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getFrontGears().getNumberOfGears()) {
                case 1 -> {
                    if (bike.getRearGears().getNumberOfGears() == 10 || bike.getRearGears().getNumberOfGears() == 11) {
                        link = chainReactionURL + "shimano-m5100-deore-10-11-speed-single-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + OTHER);
                        bike.getFrontGears().setShimanoGroupSet(OTHER);
                    } else if (bike.getRearGears().getNumberOfGears() == 12) {
                        link = chainReactionURL + "shimano-m6100-deore-12-speed-mtb-single-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + OTHER);
                        bike.getFrontGears().setShimanoGroupSet(OTHER);
                    } else {
                        link = chainReactionURL + "miche-xpress-track-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: null");
                        bike.getFrontGears().setShimanoGroupSet(null);
                    }
                }
                case 2 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = chainReactionURL + "shimano-fc-r2000-claris-compact-8-speed-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + CLARIS);
                        bike.getFrontGears().setShimanoGroupSet(CLARIS);
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = chainReactionURL + "shimano-r3000-sora-9-speed-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + SORA);
                        bike.getFrontGears().setShimanoGroupSet(SORA);
                    } else if (bike.getRearGears().getNumberOfGears() == 10) {
                        link = chainReactionURL + "shimano-tiagra-4700-10-speed-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + TIAGRA);
                        bike.getFrontGears().setShimanoGroupSet(TIAGRA);
                    } else if (bike.getRearGears().getNumberOfGears() == 11) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = chainReactionURL + "shimano-105-r7000-11-speed-road-double-chainset";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = chainReactionURL + "shimano-ultegra-r8000-11sp-road-double-chainset";
                        } else {
                            link = chainReactionURL + "shimano-ultegra-r8000-11sp-road-double-chainset";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + ULTEGRA);
                            bike.getFrontGears().setShimanoGroupSet(ULTEGRA);
                        }
                    } else if (bike.getRearGears().getNumberOfGears() == 12) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = chainReactionURL + "shimano-105-r7100-12-speed-double-chainset";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = chainReactionURL + "shimano-ultegra-r8100-12-speed-chainset";
                        } else {
                            link = chainReactionURL + "shimano-dura-ace-r9200-12-speed-chainset";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                            bike.getFrontGears().setShimanoGroupSet(DURA_ACE);
                        }
                    } else {
                        link = chainReactionURL + "miche-team-compact-2x10-speed-road-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: null");
                        bike.getFrontGears().setShimanoGroupSet(null);
                    }
                }
                case 3 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = chainReactionURL + "shimano-claris-r2000-3x8-speed-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + CLARIS);
                        bike.getFrontGears().setShimanoGroupSet(CLARIS);
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = chainReactionURL + "shimano-sora-r3030-9-speed-triple-chainset";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + SORA);
                        bike.getFrontGears().setShimanoGroupSet(SORA);
                    } else {
                        link = chainReactionURL + "shimano-tiagra-4703-10sp-road-triple-chainset";
                        LOGGER.info("Setting number of rear gears from: " + bike.getRearGears().getNumberOfGears() + ", to: 10");
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + TIAGRA);
                        bike.getFrontGears().setShimanoGroupSet(TIAGRA);
                        bike.getRearGears().setNumberOfGears(10);
                    }
                }
            }
            setBikePartsFromLink(link, "chainring");
        } catch (IOException e) {
            handleIOException("Get Chainring", e);
        }
    }

    private void getCassette() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getRearGears().getNumberOfGears()) {
                case 8 -> {
                    link = wiggleURL + "shimano-hg50-8-speed-cassette";
                    LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + CLARIS);
                    bike.getRearGears().setShimanoGroupSet(CLARIS);
                }
                case 9 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(CLARIS)) {
                        link = wiggleURL + "shimano-hg50-9-speed-road-cassette";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(SORA)) {
                        link = wiggleURL + "shimano-sora-hg400-9-speed-cassette";
                    } else {
                        link = wiggleURL + "shimano-sora-hg400-9-speed-cassette";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + SORA);
                        bike.getRearGears().setShimanoGroupSet(SORA);
                    }
                }
                case 10 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(TIAGRA)) {
                        link = chainReactionURL + "shimano-tiagra-hg500-10-speed-road-cassette-5360107149";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                        link = chainReactionURL + "shimano-105-5700-10-speed-road-cassette";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ULTEGRA)) {
                        link = chainReactionURL + "shimano-ultegra-6700-10-speed-road-cassette";
                    } else {
                        link = chainReactionURL + "shimano-ultegra-6700-10-speed-road-cassette";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + ULTEGRA);
                        bike.getRearGears().setShimanoGroupSet(ULTEGRA);
                    }
                }
                case 11 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                        link = chainReactionURL + "shimano-105-r7000-11-speed-cassette";

                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ULTEGRA)) {
                        link = chainReactionURL + "shimano-ultegra-r8000-11-speed-cassette";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(DURA_ACE)) {
                        link = chainReactionURL + "shimano-dura-ace-r9100-cassette";
                    } else {
                        link = chainReactionURL + "shimano-dura-ace-r9100-cassette";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                        bike.getRearGears().setShimanoGroupSet(DURA_ACE);
                    }
                }
                case 12 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                        link = chainReactionURL + "shimano-105-r7100-12-speed-cassette";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ULTEGRA)) {
                        link = chainReactionURL + "shimano-ultegra-r8100-12-speed-cassette";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(DURA_ACE)) {
                        link = chainReactionURL + "shimano-dura-ace-r9200-12-speed-cassette";
                    } else {
                        link = chainReactionURL + "shimano-dura-ace-r9200-12-speed-cassette";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                        bike.getRearGears().setShimanoGroupSet(DURA_ACE);
                    }
                }
            }
            setBikePartsFromLink(link, "cassette");
        } catch (IOException e) {
            handleIOException("Get Cassette", e);
        }
    }

    private void getChain() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getRearGears().getNumberOfGears()) {
                case 8 -> link = wiggleURL + "shimano-hg-40-6-8-speed-chain";
                case 9 -> link = wiggleURL + "shimano-xt-hg93-9-speed-chain";
                case 10 -> link = wiggleURL + "shimano-hg95-10-speed-chain";
                case 11 -> link = wiggleURL + "shimano-hg601q-105-5800-11-speed-chain";
                case 12 -> link = wiggleURL + "shimano-slx-m7100-12-speed-chain";
            }
            setBikePartsFromLink(link, "chain");
        } catch (IOException e) {
            handleIOException("Get Chain", e);
        }
    }

    private void getRearDerailleur() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getRearGears().getNumberOfGears()) {
                case 8 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(CLARIS)) {
                        link = wiggleURL + "shimano-claris-r2000-8-speed-rear-derailleur";
                    } else {
                        link = wiggleURL + "shimano-claris-r2000-8-speed-rear-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + CLARIS);
                        bike.getRearGears().setShimanoGroupSet(CLARIS);
                    }
                }
                case 9 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(SORA)) {
                        link = wiggleURL + "shimano-sora-r3000-9-speed-rear-derailleur";
                    } else {
                        link = wiggleURL + "shimano-sora-r3000-9-speed-rear-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + SORA);
                        bike.getRearGears().setShimanoGroupSet(SORA);
                    }
                }
                case 10 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(TIAGRA)) {
                        link = wiggleURL + "shimano-tiagra-4700-10-speed-rear-derailleur-gs";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                        link = wiggleURL + "shimano-105-5701-10-speed-rear-derailleur";
                    } else {
                        link = wiggleURL + "shimano-105-5701-10-speed-rear-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + ONE_O_FIVE);
                        bike.getRearGears().setShimanoGroupSet(ONE_O_FIVE);
                    }
                }
                case 11 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                        link = chainReactionURL + "shimano-105-r7000-11-speed-rear-derailleur";

                    } else if (bike.getRearGears().getShimanoGroupSet().equals(ULTEGRA)) {
                        link = chainReactionURL + "shimano-ultegra-r8000-11-speed-rear-derailleur";
                    } else {
                        link = chainReactionURL + "shimano-ultegra-r8000-11-speed-rear-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + ULTEGRA);
                        bike.getRearGears().setShimanoGroupSet(ULTEGRA);
                    }
                }
                case 12 -> {
                    if (bike.getRearGears().getShimanoGroupSet().equals(ULTEGRA)) {
                        link = chainReactionURL + "shimano-ultegra-r8150-di2-12-speed-rear-derailleur";
                    } else if (bike.getRearGears().getShimanoGroupSet().equals(DURA_ACE)) {
                        link = chainReactionURL + "shimano-dura-ace-r9250-di2-12-speed-rear-derailleur";
                    } else {
                        link = chainReactionURL + "shimano-dura-ace-r9250-di2-12-speed-rear-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getRearGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                        bike.getRearGears().setShimanoGroupSet(DURA_ACE);
                    }
                }
            }
            setBikePartsFromLink(link, "rear-derailleur");
        } catch (IOException e) {
            handleIOException("Get Rear Derailleur", e);
        }
    }

    private void getFrontDerailleur() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getFrontGears().getNumberOfGears()) {
                case 1 -> {
                    link = wiggleURL + "deda-dog-fang-chain-catcher";
                    LOGGER.info("Front Derailleur not required, providing chain catcher");
                }
                case 2 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = chainReactionURL + "microshift-r8-r252-double-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3000-9-speed-double-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 10) {
                        link = wiggleURL + "shimano-tiagra-fd4700-10-speed-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 11) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = wiggleURL + "shimano-105-r7000-11-speed-front-derailleur";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = wiggleURL + "shimano-ultegra-r8000-11-speed-band-on-front-derailleur";
                        } else {
                            link = wiggleURL + "shimano-dura-ace-r9100-band-on-front-derailleur";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                            bike.getFrontGears().setShimanoGroupSet(DURA_ACE);
                        }
                    } else if (bike.getRearGears().getNumberOfGears() == 12) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = wiggleURL + "shimano-105-r7150-di2-e-tube-front-derailleur";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-front-derailleur";
                        } else {
                            link = wiggleURL + "shimano-dura-ace-r9250-di2-12-speed-front-derailleur";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                            bike.getFrontGears().setShimanoGroupSet(DURA_ACE);
                        }
                    } else {
                        link = wiggleURL + "microshift-centos-r58-double-front-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + OTHER);
                        bike.getFrontGears().setShimanoGroupSet(OTHER);
                    }
                }
                case 3 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = wiggleURL + "microshift-r539-triple-9-speed-front-road-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3030-9-speed-triple-front-derailleur";
                    } else {
                        link = wiggleURL + "shimano-tiagra-4703-3x10sp-braze-on-front-mech";
                    }
                }
            }
            setBikePartsFromLink(link, "front-derailleur");
        } catch (IOException e) {
            handleIOException("Get Front Derailleur", e);
        }
    }

    private void getSTIShifters() {
        try {
            bike = fullBikeService.getBike();
            switch ((int) bike.getFrontGears().getNumberOfGears()) {
                case 1 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = wiggleURL + "microshift-r480-1x8-speed-dual-control-lever-set";
                        if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                            bike.setBrakeType(MECHANICAL_DISC);
                            LOGGER.info("Hydraulic STI not available for " + bike.getFrontGears().getNumberOfGears() + " x " + bike.getRearGears().getNumberOfGears()
                                    + "! Switched to " + bike.getBrakeType());
                        }
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = wiggleURL + "microshift-r490-1x9-speed-dual-control-lever-set";
                        if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                            bike.setBrakeType(MECHANICAL_DISC);
                            LOGGER.info("Hydraulic STI not available for " + bike.getFrontGears().getNumberOfGears() + " x " + bike.getRearGears().getNumberOfGears()
                                    + "! Switched to " + bike.getBrakeType());
                        }
                    }
                }
                case 2 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = chainReactionURL + "microshift-r8-r252-double-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3000-9-speed-double-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 10) {
                        link = wiggleURL + "shimano-tiagra-fd4700-10-speed-front-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 11) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = wiggleURL + "shimano-105-r7000-11-speed-front-derailleur";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = wiggleURL + "shimano-ultegra-r8000-11-speed-band-on-front-derailleur";
                        } else {
                            link = wiggleURL + "shimano-dura-ace-r9100-band-on-front-derailleur";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                            bike.getFrontGears().setShimanoGroupSet(DURA_ACE);
                        }
                    } else if (bike.getRearGears().getNumberOfGears() == 12) {
                        if (bike.getFrontGears().getShimanoGroupSet().equals(ONE_O_FIVE)) {
                            link = wiggleURL + "shimano-105-r7150-di2-e-tube-front-derailleur";
                        } else if (bike.getFrontGears().getShimanoGroupSet().equals(ULTEGRA)) {
                            link = wiggleURL + "shimano-ultegra-r8150-di2-12-speed-front-derailleur";
                        } else {
                            link = wiggleURL + "shimano-dura-ace-r9250-di2-12-speed-front-derailleur";
                            LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + DURA_ACE);
                            bike.getFrontGears().setShimanoGroupSet(DURA_ACE);
                        }
                    } else {
                        link = wiggleURL + "microshift-centos-r58-double-front-derailleur";
                        LOGGER.info("Setting Groupset from: " + bike.getFrontGears().getShimanoGroupSet() + ", to: " + OTHER);
                        bike.getFrontGears().setShimanoGroupSet(OTHER);
                    }
                }
                case 3 -> {
                    if (bike.getRearGears().getNumberOfGears() == 8) {
                        link = wiggleURL + "microshift-r539-triple-9-speed-front-road-derailleur";
                    } else if (bike.getRearGears().getNumberOfGears() == 9) {
                        link = wiggleURL + "shimano-sora-r3030-9-speed-triple-front-derailleur";
                    } else {
                        link = wiggleURL + "shimano-tiagra-4703-3x10sp-braze-on-front-mech";
                    }
                }
            }
            setBikePartsFromLink(link, "shifters");
        } catch (IOException e) {
            handleIOException("Get STI Shifters", e);
        }
    }

    private void getLeverShifters() {
        try {
            bike = fullBikeService.getBike();
            if (bike.getBrakeType().equals(HYDRAULIC_DISC)) {
                link = wiggleURL + "shimano-mt410-deore-disc-brake-mt401-lever";
            } else {
                link = wiggleURL + "shimano-alivio-t4000-brake-levers";
            }
            setBikePartsFromLink(link, "brake-levers");
        } catch (IOException e) {
            handleIOException("Get Lever Shifters", e);
        }
    }

    public void setBikePartsFromLink(String link, String part) throws IOException {
        bike = fullBikeService.getBike();
        Document doc = Jsoup.connect(link).get();
        Element e = doc.select("div.ProductDetail_container__Z7Hge").get(0);
        String name = Objects.requireNonNull(e.select("h1").first()).text();
        String price = Objects.requireNonNull(e.select("div.ProductPrice_productPrice__cWyiO").select("p").first()).text().replace("£", "").split(" ")[0];
        LOGGER.info("Found Product: " + name);
        LOGGER.info("For Price: " + price);
        LOGGER.info("Link: " + link);
        switch (part) {
            case "chain" -> {
                bike.getBikeParts().setChainName(name);
                bike.getBikeParts().setChainPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "bar" -> {
                bike.getBikeParts().setHandlebarName(name);
                bike.getBikeParts().setHandlebarPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "cassette" -> {
                bike.getBikeParts().setCassetteName(name);
                bike.getBikeParts().setCassettePrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "chainring" -> {
                bike.getBikeParts().setChainringName(name);
                bike.getBikeParts().setChainringPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "rear-derailleur" -> {
                bike.getBikeParts().setRearDeraileurName(name);
                bike.getBikeParts().setRearDeraileurPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "front-derailleur" -> {
                bike.getBikeParts().setFrontDeraileurName(name);
                bike.getBikeParts().setFrontDeraileurPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "brake-lever" -> {
                bike.getBikeParts().setBrakeLeverName(name);
                bike.getBikeParts().setBrakeLeverPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
            case "shifters" -> {
                bike.getBikeParts().setShifterName(name);
                bike.getBikeParts().setShifterPrice(new BigDecimal(price));
                bike.getBikeParts().getWeblinks().add(link);
            }
        }
    }

    private void handleIOException(String message, IOException e) {
        LOGGER.error("An IOException occurred from: " + message + "! " + e.getMessage());
    }
}