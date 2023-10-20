package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.BikeParts;
import com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class BikePartsService {

    private static final Logger LOGGER = LogManager.getLogger(BikePartsService.class);
    private static final String chainReactionURL = "https://www.chainreactioncycles.com/p/";
    private static final String wiggleURL = "https://www.wiggle.com/p/";
    private static final String dolanURL = "https://www.dolan-bikes.com/";
    private static final String genesisURL = "https://www.genesisbikes.co.uk/";
    private static String link;
    private static FullBike bike;

    @Autowired
    FullBikeService fullBikeService;

    @Autowired
    private ShimanoGroupsetService shimanoGroupsetService;

    public void getBikePartsForBike() throws Exception {
        bike = fullBikeService.getBike();
        BikeParts bikeParts = new BikeParts();
        bike.setBikeParts(bikeParts);
        getHandlebarParts();
        getFrameParts();
        getGearSet();
        calculateTotalPrice();
    }

    private void getGearSet() throws IOException {
        bike = fullBikeService.getBike();
        if (Objects.requireNonNull(bike.getGroupsetBrand()) == GroupsetBrand.SHIMANO) {
            shimanoGroupsetService.getShimanoGroupset();
            //            case SRAM:
//                getSramGroupset(bike);
//                break;
//            case CAMPAGNOLO:
//                getCampagnoloGroupset(bike);
        }
    }

    private void getHandlebarParts() throws Exception {
        bike = fullBikeService.getBike();
        LOGGER.info("Method for Getting Handlebar Parts from web");
        switch (bike.getHandleBarType()) {
            case DROPS -> link = chainReactionURL + "fsa-omega-compact-road-handlebar";
            case FLAT -> link = chainReactionURL + "spank-spoon-35-mountain-bike-riser-handlebar";
            case BULLHORNS -> link = chainReactionURL + "cinelli-bullhorn-road-handlebar";
            case FLARE -> link = chainReactionURL + "spank-flare-25-vibrocore-drop-handlebar";
        }
        setBikePartsFromLink(link, "bar");
    }

    private void getFrameParts() throws IOException {
        bike = fullBikeService.getBike();
        LOGGER.info("Jsoup Method for Getting Frame Parts");
        String frameName = "";
        String framePrice = "";
        Element e;
        Document doc;
        switch (bike.getFrame().getFrameStyle()) {
            case ROAD -> {
                if (bike.getFrame().isDiscBrakeCompatible()) {
                    link = dolanURL + "dolan-rdx-aluminium-disc--frameset/";
                } else {
                    link = dolanURL + "dolan-preffisio-aluminium-road--frameset/";
                }
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = e.select("div.price").select("span").first().text();
            }
            case TOUR -> {
                link = genesisURL + "genesis-fugio-frameset-vargn22330/";
                doc = Jsoup.connect(link).get();
                frameName = doc.select("h1.page-title").get(0).select("span").first().text();
                framePrice = doc.select("div.price-box.price-final_price").get(0).select("span.price").first().text();
            }
            case GRAVEL -> {
                link = dolanURL + "dolan-gxa2020-aluminium-gravel-frameset/";
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = (e.select("div.price").select("span").first().text());
            }
            case SINGLE_SPEED -> {
                link = dolanURL + "dolan-pre-cursa-aluminium-frameset/";
                doc = Jsoup.connect(link).get();
                e = doc.select("div.productBuy > div.productPanel").get(0);
                frameName = e.select("h1").first().text();
                framePrice = (e.select("div.price").select("span").first().text());
            }
        }
        LOGGER.info("Found Frame: " + frameName);
        LOGGER.info("For price: " + framePrice);
        LOGGER.info("Frame link: " + link);
        bike.getBikeParts().getListOfParts().add(new Part("Frame", frameName, new BigDecimal(framePrice), link));
    }

    public void setBikePartsFromLink(String link, String component) throws IOException {
        bike = fullBikeService.getBike();
        Document doc = Jsoup.connect(link).get();
        Element e = doc.select("div.ProductDetail_container__Z7Hge").get(0);
        String name = e.select("h1").first().text();
        String price = e.select("div.ProductPrice_productPrice__cWyiO").select("p").first().text().replace("£", "").split(" ")[0];
        LOGGER.info("Found Product: " + name);
        LOGGER.info("For Price: " + price);
        LOGGER.info("Link: " + link);
        bike.getBikeParts().getListOfParts().add(new Part(component, name, new BigDecimal(price), link));
    }

    private void calculateTotalPrice() {
        BigDecimal total = BigDecimal.valueOf(0);
        bike = fullBikeService.getBike();
        ArrayList<BigDecimal> prices = new ArrayList<>();
        for (Part p : bike.getBikeParts().getListOfParts()) {
            prices.add(p.getPrice());
        }
        for (BigDecimal bd : prices) {
            if (bd != null) {
                total = total.add(bd);
            }
        }
        total = total.setScale(2, RoundingMode.UP);
        bike.getBikeParts().setTotalBikePrice(total);
    }
}