package com.homeapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.services.FuelPriceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Level.*;


@SpringBootApplication(scanBasePackages = "com.homeapp.backend")
public class backend implements CommandLineRunner {

    static Logger logger = Logger.getLogger(backend.class.getName());
    private static final Set<Part> problemParts = new HashSet<>();
    private static final Set<Part> writeParts = new HashSet<>();
    private static final String LINKS_FILE = "src/main/resources/links.json";
    private static final ObjectMapper om = new ObjectMapper();
    private static final String today = LocalDate.now().toString();
    private static String price;
    private static final FuelPriceService fuelPriceService = new FuelPriceService();

    public static void main(String[] args) throws IOException {
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();
        logManager.readConfiguration(new FileInputStream("src/conf/logging.properties"));
        SpringApplication.run(backend.class, args);
        fuelPriceService.run();
        checkAllLinks();
    }

    /**
     * A method that runs through the manually updated list of links in the links.json file.
     * Collects all problem links and sends these to reporter
     */
    public static void checkAllLinks() {
        logger.log(INFO, "Started checking all links!");
        List<Part> allParts = readLinksFile();
        for (Part part : allParts) {
            try {
                int statusCode = Jsoup.connect(part.getLink()).execute().statusCode();
                writeParts.add(part);
                if (statusCode == 200) {
                    setPartAttributesFromLink(part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } catch (IOException e) {
                addFailedPartToWriteList(part);
            }
        }
        logger.log(WARNING, "**** Please check the following links ****");
        logger.log(WARNING, "You have " + problemParts.size() + " issues with links ref doc!!\nSee each problem part listed below.");
        problemParts.forEach(part -> logger.log(SEVERE, "Internal ref: " + part.getInternalReference() + "\nLink: " + part.getLink()));
        writePartsToFile();
        logger.log(WARNING, "**** Checking links complete ****");
        logger.log(INFO, "Finished checking links!");
    }

    /**
     * Sets bike parts price and name on the part that is passed-in.
     * Single method used to access website and skim information. This is then used to populate Part Object.
     * BikeParts Object on instance is then updated with the new Part object.
     *
     * @param part the part that is to updated
     */
    private static void setPartAttributesFromLink(Part part) {
        price = "";
        try {
            String name = part.getName();
            price = part.getPrice();
            Document doc = Jsoup.connect(part.getLink()).timeout(5000).get();
            Optional<Element> e;
            Optional<Element> priceElement;
            if (part.getLink().contains("dolan-bikes")) {
                e = Optional.ofNullable(doc.selectFirst("div.productBuy > div.productPanel"));
                if (e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().selectFirst("h1"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.price"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } else if (part.getLink().contains("evans")) {
                e = Optional.ofNullable(doc.getElementById("productDetails"));
                if (e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().getElementById("lblProductName"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().getElementById("lblSellingPrice"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } else if (part.getLink().contains("wiggle") || part.getLink().contains("chainreactioncycles")) {

                e = Optional.ofNullable(doc.getElementById("productDetails"));
                if (e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().getElementById("lblProductName"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().getElementById("lblSellingPrice"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } else if (part.getLink().contains("halfords")) {
                e = Optional.ofNullable(doc.getElementById("productInfoBlock"));
                if (e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().select("h1").first())
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.b-price__sale"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } else if (part.getLink().contains("sjscycles")) {
                e = Optional.of(doc);
                if (!e.isPresent() || e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().selectFirst("title"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.f-xxxlarge"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    addFailedPartToWriteList(part);
                }
            } else if (part.getLink().contains("halo")) {
                e = Optional.ofNullable(doc.selectFirst("div.productDetails"));
                if (e.isEmpty()) {
                    addFailedPartToWriteList(part);
                    return;
                }
                name = Optional.ofNullable(e.get().select("h1").first())
                        .map(Element::text)
                        .orElseGet(() -> {
                            addFailedPartToWriteList(part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary"));
                if (priceElement.isPresent()) {
                    if (e.get().selectFirst("div.priceSummary > ins") != null) {
                        priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary > ins > span"));
                        if (priceElement.isPresent()) {
                            setPartPricing(priceElement.get(), part);
                        } else {
                            addFailedPartToWriteList(part);
                        }
                    } else {
                        priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary > span"));
                        if (priceElement.isPresent()) {
                            setPartPricing(priceElement.get(), part);
                        } else {
                            addFailedPartToWriteList(part);
                        }
                    }
                } else {
                    addFailedPartToWriteList(part);
                }
            } else {
                logger.log(WARNING, "Trying to use unknown website " + part.getLink());
                addFailedPartToWriteList(part);

            }
            logger.log(INFO, "Found: " + name + "\nFor: " + price + "\nFrom: " + part.getLink());
            part.setName(name);
            part.setPrice(price);
        } catch (
                IOException e) {
            addFailedPartToWriteList(part);
        }
    }

    /**
     * Writes unique list of Parts back to file, to allow information to be retrieved directly from file later.
     * Uses the class Set writeParts as this list is accumulated through the startup process.
     */
    private static void writePartsToFile() {
        logger.log(INFO, "Writing updated Bike Parts to file");
        try {
            om.writeValue(new File(LINKS_FILE), writeParts);
        } catch (Exception e) {
            logger.log(SEVERE, "An exception occurred writing ALL parts to file!!\n" + e.getMessage());
        }
    }

    private static List<Part> readLinksFile() {
        logger.log(INFO, "Reading all Links from File");
        try {
            return om.readValue(new File(LINKS_FILE), new TypeReference<>() {
            });
        } catch (Exception e) {
            logger.log(SEVERE, "An IOException occurred reading all links file!!\n" + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void run(String @NonNull ... args) {

    }

    private static void setPartPricing(Element element, Part part) {
        if (!element.text().isEmpty()) {
            price = element.text();
            if (price.contains("£")) {
                int firstIndex = price.indexOf("£");
                int secondIndex = price.indexOf("£", firstIndex + 1);
                if (secondIndex != -1) {
                    price = price.substring(0, secondIndex).trim();
                }
            }
            price = price.replaceAll("[^\\d.]", "");
            if (price.contains(".")) {
                price = price.split("\\.")[0] + "." + price.split("\\.")[1].substring(0, 2);
            } else {
                price = price + ".00";
            }
            part.setIsUptoDate(true);
            part.setDateLastUpdated(today);
        } else {
            logger.log(WARNING, "Bike part: " + part.getInternalReference() + " is not up to date");
            part.setIsUptoDate(false);
        }
    }

    /**
     * Keeps failed links in file output while marking them as out-of-date.
     */
    private static void addFailedPartToWriteList(Part part) {
        part.setIsUptoDate(false);
        problemParts.add(part);
        writeParts.add(part);
    }
}