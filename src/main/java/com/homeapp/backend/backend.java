package com.homeapp.backend;

import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import com.homeapp.backend.services.FuelPriceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

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
    private static final InfoLogger infoLogger = new InfoLogger();
    private static final WarnLogger warnLogger = new WarnLogger();
    private static final ErrorLogger errorLogger = new ErrorLogger();
    private static final String today = LocalDate.now().toString();
    private static String price;
    private static final FuelPriceService fuelPriceService = new FuelPriceService();

    public static void main(String[] args) throws IOException {
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();
        logManager.readConfiguration(new FileInputStream("src/conf/logging.properties"));
        logger.log(INFO, "Starting UP!!");
        logger.log(WARNING, "Starting UP!!");
        logger.log(SEVERE, "Starting UP!!");
        SpringApplication.run(backend.class, args);
        fuelPriceService.run();
        checkAllLinks();
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
                warnLogger.log("Trying to use unknown website " + part.getLink());
                addFailedPartToWriteList(part);

            }
            warnLogger.log("Found: " + name + "\nFor: " + price + "\nFrom: " + part.getLink());
            part.setName(name);
            part.setPrice(price);
        } catch (
                IOException e) {
            addFailedPartToWriteList(part);
            warnLogger.log("Error adding price for part: " + part.getInternalReference());
        }
    }

    /**
     * A method that runs through the manually updated list of links in the links.json file.
     * Collects all problem links and sends these to reporter
     */
    public static void checkAllLinks() {
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
        errorLogger.log("**** Please check the following links ****");
        errorLogger.log("You have " + problemParts.size() + " issues with links ref doc!!");
        problemParts.forEach(part -> errorLogger.log("Internal ref: " + part.getInternalReference() + "\nLink: " + part.getLink()));
        writePartsToFile();
        errorLogger.log("**** Checking links complete ****");
        infoLogger.log("Finished checking links!");
    }

    /**
     * Writes unique list of Parts back to file, to allow information to be retrieved directly from file later.
     * Uses the class Set writeParts as this list is accumulated through the startup process.
     */
    private static void writePartsToFile() {
        infoLogger.log("Writing updated Bike Parts to file");
        try {
            om.writeValue(new File(LINKS_FILE), writeParts);
        } catch (Exception e) {
            errorLogger.log("An exception occurred writing ALL parts to file!!\n" + e.getMessage());
        }
    }

    private static List<Part> readLinksFile() {
        infoLogger.log("Reading all Links from File");
        try {
            return om.readValue(new File(LINKS_FILE), new TypeReference<>() {
            });
        } catch (Exception e) {
            errorLogger.log("An IOException occurred reading all links file!!\n" + e.getMessage());
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
            part.setIsUptoDate(false);
        }
    }

    /**
     * Keeps failed links in file output while marking them as out-of-date.
     */
    private static void addFailedPartToWriteList(Part part) {
        problemParts.add(part);
        part.setIsUptoDate(false);
        writeParts.add(part);
    }
}