package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;

@Service
@Scope("singleton")
public class BikePartsService {

    private static final Logger LOGGER = LogManager.getLogger(BikePartsService.class);
    private static final String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static final String wiggleURL = "https://www.wiggle.com/p/";
    private static final String dolanURL = "https://www.dolan-bikes.com/";
    private static final String genesisURL = "https://www.genesisbikes.co.uk/";
    private static String link;
    private static FullBike bike;
    private BikeParts bikeParts;

    @Autowired
    FullBikeService fullBikeService;

    @Autowired
    private ShimanoGroupsetService shimanoGroupsetService;

    public BikeParts getBikePartsForBike() {
        bikeParts = new BikeParts();
        try {
            bike = fullBikeService.getBike();
            CompletableFuture<Void> handleBarFuture = CompletableFuture.runAsync(this::getHandlebarParts);
            CompletableFuture<Void> frameFuture = CompletableFuture.runAsync(this::getFrameParts);
            CompletableFuture<Void> gearFuture = CompletableFuture.runAsync(this::getGearSet);
            CompletableFuture.allOf(handleBarFuture,frameFuture,gearFuture).join();
            calculateTotalPrice();
        } catch (Exception e) {
            handleException("Get Bike Parts", e);
        }
        return bikeParts;
    }

    private void getGearSet() {
        bike = fullBikeService.getBike();
        bike.setGroupsetBrand(SHIMANO);
        shimanoGroupsetService.getShimanoGroupset(bikeParts);
    }

    private void getHandlebarParts() {
        try {
            bike = fullBikeService.getBike();
            LOGGER.info("Method for Getting Handlebar Parts from web");
            switch (bike.getHandleBarType()) {
                case DROPS -> link = chainReactionURL + "fsa-omega-compact-road-handlebar";
                case FLAT -> link = chainReactionURL + "spank-spoon-35-mountain-bike-riser-handlebar";
                case BULLHORNS -> link = chainReactionURL + "cinelli-bullhorn-road-handlebar";
                case FLARE -> link = chainReactionURL + "ritchey-comp-venturemax-handlebar";
            }
            shimanoGroupsetService.setBikePartsFromLink(link, "bar");
        } catch (Exception e) {
            handleException("Get HandleBar Parts", e);
        }
    }

    private void getFrameParts() {
        try {
            bike = fullBikeService.getBike();
            LOGGER.info("Jsoup Method for Getting Frame Parts");
            String frameName;
            String framePrice;
            Document doc;
            String link = "";
            switch (bike.getFrame().getFrameStyle()) {
                case ROAD -> {
                    if (bike.getFrame().isDiscBrakeCompatible()) {
                        link = dolanURL + "dolan-rdx-aluminium-disc--frameset/";
                    } else {
                        link = dolanURL + "dolan-preffisio-aluminium-road--frameset/";
                    }
                }
                case TOUR -> {
                    link = genesisURL + "genesis-fugio-frameset-vargn22330/";
                }
                case GRAVEL -> {
                    link = dolanURL + "dolan-gxa2020-aluminium-gravel-frameset/";
                }
                case SINGLE_SPEED -> {
                    link = dolanURL + "dolan-pre-cursa-aluminium-frameset/";
                }
            }

            doc = Jsoup.connect(link).get();

            if (link.contains("dolan-bikes")) {
                frameName = doc.select("div.productBuy > div.productPanel").first().select("h1").first().text();
                framePrice = doc.select("div.productBuy > div.productPanel").first().select("div.price").select("span.price").first().text();
            } else if (link.contains("genesisbikes")) {
                frameName = doc.select("h1.page-title span").first().text();
                framePrice = doc.select("div.price-box.price-final_price span.price").first().text();
            } else {
                frameName = "";
                framePrice = "";
            }

            framePrice = framePrice.replaceAll("[^\\d.]", "");
            framePrice = framePrice.split("\\.")[0] + "." + framePrice.split("\\.")[1].substring(0, 2);
            bikeParts.getListOfParts().add(new Part("Frame", frameName, new BigDecimal(framePrice), link));

            LOGGER.info("Found Frame: " + frameName);
            LOGGER.info("For price: " + framePrice);
            LOGGER.info("Frame link: " + link);
        } catch (IOException e) {
            handleException("Get Frame Parts", e);
        }
    }

    private void calculateTotalPrice() {
        BigDecimal total = bikeParts.getListOfParts()
                .stream()
                .map(Part::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.UP);
        bikeParts.setTotalBikePrice(total);
    }

    private void handleException(String message, Exception e) {
        LOGGER.error("An IOException occurred from: " + message + "! " + e.getMessage());
    }
}