package com.homeapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication(scanBasePackages = "com.homeapp.backend")
public class backend implements CommandLineRunner {
    private static final String LINKS_FILE = "src/main/resources/links.json";
    private static final ObjectMapper om = new ObjectMapper();
    private static final InfoLogger infoLogger = new InfoLogger();
    private static final WarnLogger warnLogger = new WarnLogger();
    private static final ErrorLogger errorLogger = new ErrorLogger();

    public static void main(String[] args) {
        checkAllLinks();
        SpringApplication.run(backend.class, args);
    }

    @Override
    public void run(String... args) {

    }

    /**
     * A method that runs through the manually updated list of links in the links.json file.
     * Collects all problem links and sends these to reporter
     */
    public static void checkAllLinks() {
        List<Part> allParts = readLinksFile();
        LinkedList<String> problemLinks = new LinkedList<>();
        LinkedList<Part> partListToWriteToFile = new LinkedList<>();
        for (Part part : allParts) {
            try {
                int statusCode = Jsoup.connect(part.getLink()).execute().statusCode();
                if (statusCode == 200) {
                    setPartAttributesFromLink(part);
                    partListToWriteToFile.add(part);
                } else {
                    problemLinks.add(part.getLink());
                    partListToWriteToFile.add(part);
                }
            } catch (IOException e) {
                problemLinks.add(part.getLink());
            }
        }
        writePartsToFile(partListToWriteToFile);
        errorLogger.log("**** Please check the following links ****");
        errorLogger.log("You have " + problemLinks.size() + " links with issues");
        problemLinks.forEach(entry -> errorLogger.log("Issue with link: " + entry));
        errorLogger.log("**** Checking links complete ****");
        infoLogger.log("Finished checking links!");
    }

    /**
     * Writes unique list of Parts back to file, to allow information to be retrieved directly from file later.
     *
     * @param updatedParts unique list of Parts to be written back to File.
     */
    private static void writePartsToFile(LinkedList<Part> updatedParts) {
        infoLogger.log("Writing updated Bike Parts to file");
        try {
            om.writeValue(new File(LINKS_FILE), updatedParts);
        } catch (IOException e) {
            errorLogger.log("An IOException occurred from method: writePartsBackFile!!See error message: " + e.getMessage() + "!!From: " + backend.class);
        }
    }

    private static List<Part> readLinksFile() {
        infoLogger.log("Reading all Links from File");
        try {
            return om.readValue(new File(LINKS_FILE), new TypeReference<>() {
            });
        } catch (IOException e) {
            errorLogger.log("An IOException occurred from method: readLinksFile!!See error message: " + e.getMessage() + "!!From: " + backend.class);
        }
        return new ArrayList<>();
    }

    /**
     * Sets bike parts price and name on the part that is passed-in.
     * Single method used to access website and skim information. This is then used to populate Part Object.
     * BikeParts Object on instance is then updated with the new Part object.
     *
     * @param part the part that is to updated
     */
    static void setPartAttributesFromLink(Part part) {
        try {
            Document doc = Jsoup.connect(part.getLink()).timeout(5000).get();
            String today = LocalDate.now().toString();
            Optional<Elements> e;
            String name = "";
            String price = "";
            if (part.getLink().contains("dolan-bikes")) {
                e = Optional.of(doc.select("div.productBuy > div.productPanel"));
                if (e.get().isEmpty()) {
                    errorLogger.log("An Error occurred !!Connecting to link: " + part.getLink() + "!!For bike Component: " + part.getComponent());
                    return;
                } else {
                    name = e.get().select("h1").first().text();
                    price = e.get().select("div.price").select("span.price").first().text();
                }
            } else if (part.getLink().contains("genesisbikes")) {
                e = Optional.of(doc.select("div.product-info-main-header"));
                if (e.get().isEmpty()) {
                    errorLogger.log("An Error occurred !!Connecting to link: " + part.getLink() + "!!For bike Component: " + part.getComponent());
                    return;
                } else {
                    name = e.get().select("h1.page-title").text();
                    price = e.get().select("div.product-info-price > div.price-final_price").first().select("span").text();
                }
            } else if (part.getLink().contains("wiggle") || part.getLink().contains("chainreactioncycles")) {
                e = Optional.of(doc.select("div.ProductDetail_container__FX6xF"));
                if (e.get().isEmpty()) {
                    errorLogger.log("An Error occurred !!Connecting to link: " + part.getLink() + "!!For bike Component: " + part.getComponent());
                    return;
                } else {
                    name = e.get().select("h1").first().text();
                    price = e.get().select("div.ProductPrice_productPrice__Fg1nA").select("p").first().text();
                }
            } else if (part.getLink().contains("halo")) {
                e = Optional.of(doc.select("div.ProductDetail_container__FX6xF"));
                if (e.get().isEmpty()) {
                    errorLogger.log("An Error occurred !!Connecting to link: " + part.getLink() + "!!For bike Component: " + part.getComponent());
                    return;
                } else {
                    name = e.get().select("h1").first().text();
                    if (e.get().select("div.priceSummary").select("ins").first() != null) {
                        price = e.get().select("div.priceSummary").select("ins").select("span").first().text().replace("£", "").split(" ")[0];
                    } else {
                        price = e.get().select("div.priceSummary").select("span").first().text().replace("£", "").split(" ")[0];
                    }
                    e.get().select("div.priceSummary").select("ins").first();
                }
            }
            price = price.replaceAll("[^\\d.]", "");
            price = price.split("\\.")[0] + "." + price.split("\\.")[1].substring(0, 2);
            if (!price.contains(".")) {
                price = price + ".00";
            }
            warnLogger.log("Found Frame: " + name);
            warnLogger.log("For price: " + price);
            warnLogger.log("Frame link: " + part.getLink());
            part.setDateLastUpdated(today);
            part.setName(name);
            part.setPrice(price);
        } catch (IOException e) {
            errorLogger.log("An IOException occurred from: getPartFromLink!!See error message: " + e.getMessage() + "!!For bike Component: " + part.getComponent());
        }
    }
}