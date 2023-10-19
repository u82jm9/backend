package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
public class BikePartsService {

    private static Logger LOGGER = LogManager.getLogger(BikePartsService.class);
    private static String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static String wiggleURL = "https://www.wiggle.com/p/";
    private static String dolanURL = "https://www.dolan-bikes.com/";
    private static String genesisURL = "https://www.genesisbikes.co.uk/";
    private static String link;

    @Autowired
    private ShimanoGroupsetService shimanoGroupsetService;

    public void getBikePartsForBike(FullBike b) throws Exception {
        ArrayList<String> links = new ArrayList<>();
        BikeParts bikeParts = new BikeParts();
        bikeParts.setWeblinks(links);
        b.setBikeParts(bikeParts);
        getHandlebarParts(b);
        getFrameParts(b);
        getGearSet(b);
        calculateTotalPrice(b.getBikeParts());
    }

    private void getGearSet(FullBike bike) throws IOException {
        switch (bike.getGroupsetBrand()) {
            case SHIMANO:
                shimanoGroupsetService.getShimanoGroupset();
                break;
//            case SRAM:
//                getSramGroupset(bike);
//                break;
//            case CAMPAGNOLO:
//                getCampagnoloGroupset(bike);
        }
    }

    private void getHandlebarParts(FullBike bike) throws Exception {
        LOGGER.info("Method for Getting Handlebar Parts from web");
        switch (bike.getHandleBarType()) {
            case DROPS:
                link = chainReactionURL + "fsa-omega-compact-road-handlebar";
                break;
            case FLAT:
                link = chainReactionURL + "spank-spoon-35-mountain-bike-riser-handlebar";
                break;
            case BULLHORNS:
                link = chainReactionURL + "cinelli-bullhorn-road-handlebar";
                break;
            case FLARE:
                link = chainReactionURL + "spank-flare-25-vibrocore-drop-handlebar";
                break;
        }
        setBikePartsFromLink(bike, link, "bar");
    }

    private void getFrameParts(FullBike bike) throws IOException {
        LOGGER.info("Jsoup Method for Getting Frame Parts");
        String frameName = "";
        String framePrice = "";
        Element e;
        Document doc;
        switch (bike.getFrame().getFrameStyle()) {
            case ROAD:
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    link = dolanURL + "dolan-rdx-aluminium-disc--frameset/";
                } else {
                    link = dolanURL + "dolan-preffisio-aluminium-road--frameset/";
                }
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = e.select("div.price").select("span").first().text();
                break;
            case TOUR:
                link = genesisURL + "genesis-fugio-frameset-vargn22330/";
                doc = Jsoup.connect(link).get();
                frameName = doc.select("h1.page-title").get(0).select("span").first().text();
                framePrice = doc.select("div.price-box.price-final_price").get(0).select("span.price").first().text();
                break;
            case GRAVEL:
                link = dolanURL + "dolan-gxa2020-aluminium-gravel-frameset/";
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = (e.select("div.price").select("span").first().text());
                break;
            case SINGLE_SPEED:
                link = dolanURL + "dolan-pre-cursa-aluminium-frameset/";
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = (e.select("div.price").select("span").first().text());
                break;
        }
        LOGGER.info("Found Frame: " + frameName);
        LOGGER.info("For price: " + framePrice);
        LOGGER.info("Frame link: " + link);
        bike.getBikeParts().setFrameName(frameName);
        bike.getBikeParts().setFramePrice(new BigDecimal(framePrice.replace("£", "").split(" ")[0]));
        bike.getBikeParts().getWeblinks().add(link);
    }

    public void setBikePartsFromLink(FullBike bike, String link, String part) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Element e = doc.select("div.ProductDetail_container__Z7Hge").get(0);
        String name = e.select("h1").first().text();
        String price = e.select("div.ProductPrice_productPrice__cWyiO").select("p").first().text().replace("£", "").split(" ")[0];
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
        }
    }

    private void calculateTotalPrice(BikeParts parts) {
        BigDecimal total = BigDecimal.valueOf(0);
        ArrayList<BigDecimal> prices = new ArrayList<>();
        prices.add(parts.getFramePrice());
        prices.add(parts.getHandlebarPrice());
        prices.add(parts.getRearDeraileurPrice());
        prices.add(parts.getFrontDeraileurPrice());
        prices.add(parts.getCassettePrice());
        prices.add(parts.getChainPrice());
        prices.add(parts.getChainringPrice());
        prices.add(parts.getBrakesPrice());
        for (BigDecimal bd : prices) {
            if (bd != null) {
                total = total.add(bd);
            }
        }
        total = total.setScale(2, RoundingMode.UP);
        parts.setTotalBikePrice(total);
    }
}