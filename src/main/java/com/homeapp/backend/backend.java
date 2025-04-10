package com.homeapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.backend.models.bike.Part;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication(scanBasePackages = "com.homeapp.backend")
public class backend implements CommandLineRunner {
    private static final String LINKS_FILE = "src/main/resources/links.json";
    private static final ObjectMapper om = new ObjectMapper();
    private static final InfoLogger infoLogger = new InfoLogger();
    private static final WarnLogger warnLogger = new WarnLogger();
    private static final ErrorLogger errorLogger = new ErrorLogger();
    private static final String today = LocalDate.now().toString();
    private static String price;

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
        Set<Part> problemParts = new HashSet<>();
        LinkedList<Part> partListToWriteToFile = new LinkedList<>();
        for (Part part : allParts) {
            try {
                int statusCode = Jsoup.connect(part.getLink()).execute().statusCode();
                partListToWriteToFile.add(part);
                if (statusCode == 200) {
                    setPartAttributesFromLink(problemParts, part);
                } else {
                    invalidPart(problemParts, part);
                }
            } catch (IOException e) {
                invalidPart(problemParts, part);
            }
        }
        writePartsToFile(partListToWriteToFile);
        errorLogger.log("**** Please check the following links ****");
        errorLogger.log("You have " + problemParts.size() + " issues with links ref doc");
        problemParts.forEach(part -> errorLogger.log("Internal ref: " + part.getInternalReference() + "\nLink: " + part.getLink()));
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
    static void setPartAttributesFromLink(Set<Part> problemParts, Part part) {
        price = "";
        try {
            String name = part.getName();
            price = part.getPrice();
            Document doc = Jsoup.connect(part.getLink()).timeout(5000).get();
            Optional<Element> e = null;
            Optional<Element> priceElement = null;
            if (part.getLink().contains("dolan-bikes")) {
                e = Optional.ofNullable(doc.selectFirst("div.productBuy > div.productPanel"));
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().selectFirst("h1"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.price"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    invalidPart(problemParts, part);
                }
            } else if (part.getLink().contains("evans")) {
                e = Optional.ofNullable(doc.getElementById("productDetails"));
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().getElementById("lblProductName"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().getElementById("lblSellingPrice"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    invalidPart(problemParts, part);
                }
            } else if (part.getLink().contains("wiggle") || part.getLink().contains("chainreactioncycles")) {

                e = Optional.ofNullable(doc.getElementById("productDetails"));
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().getElementById("lblProductName"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().getElementById("lblSellingPrice"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    invalidPart(problemParts, part);
                }
            } else if (part.getLink().contains("halfords")) {
                e = Optional.ofNullable(doc.getElementById("productInfoBlock"));
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().select("h1").first())
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.b-price__sale"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    invalidPart(problemParts, part);
                }
            } else if (part.getLink().contains("sjscycles")) {
                e = Optional.ofNullable(doc);
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().selectFirst("title"))
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("span.f-xxxlarge"));
                if (priceElement.isPresent()) {
                    setPartPricing(priceElement.get(), part);
                } else {
                    invalidPart(problemParts, part);
                }
            } else if (part.getLink().contains("halo")) {
                e = Optional.ofNullable(doc.selectFirst("div.productDetails"));
                if (e.isEmpty()) {
                    invalidPart(problemParts, part);
                    return;
                }
                name = Optional.ofNullable(e.get().select("h1").first())
                        .map(Element::text)
                        .orElseGet(() -> {
                            invalidPart(problemParts, part);
                            return part.getName();
                        });
                priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary"));
                if (priceElement.isPresent()) {
                    if (e.get().selectFirst("div.priceSummary > ins") != null) {
                        priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary > ins > span"));
                        if (priceElement.isPresent()) {
                            setPartPricing(priceElement.get(), part);
                        } else {
                            invalidPart(problemParts, part);
                        }
                    } else {
                        priceElement = Optional.ofNullable(e.get().selectFirst("div.priceSummary > span"));
                        if (priceElement.isPresent()) {
                            setPartPricing(priceElement.get(), part);
                        } else {
                            invalidPart(problemParts, part);
                        }
                    }
                } else {
                    invalidPart(problemParts, part);
                }
            } else {
                errorLogger.log("Trying to use unknown website");
                invalidPart(problemParts, part);

            }
            warnLogger.log("Found: " + name);
            warnLogger.log("For: " + price);
            warnLogger.log("From: " + part.getLink());
            part.setName(name);
            part.setPrice(price);
        } catch (
                IOException e) {
            invalidPart(problemParts, part);
            errorLogger.log("An IOException occurred from: getPartFromLink!!See error message: " + e.getMessage() + "!!For bike Component: " + part.getComponent());
        }
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

    private static void invalidPart(Set<Part> problemParts, Part part) {
        problemParts.add(part);
        part.setIsUptoDate(false);
    }
}