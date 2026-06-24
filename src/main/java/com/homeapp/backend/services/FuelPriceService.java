package com.homeapp.backend.services;

import com.homeapp.backend.models.FuelPrice;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FuelPriceService {
    private static final List<FuelPrice> totalPriceList = new ArrayList<>();
    private static final Comparator<FuelPrice> BRAND_SORT_COMPARATOR =
            Comparator.comparing((FuelPrice fp) -> fp.getBrand() == null ? "" : fp.getBrand().toLowerCase(Locale.ROOT))
                    .thenComparing(fp -> fp.getFuelType() == null ? "" : fp.getFuelType().toLowerCase(Locale.ROOT))
                    .thenComparing(fp -> fp.getPrice() == null ? "" : fp.getPrice())
                    .thenComparing(fp -> fp.getDateUpdated() == null ? "" : fp.getDateUpdated());
    private static final String DIESEL_URL =
            "https://docs.google.com/spreadsheets/d/1pUPeCiR0DFJtwC7r0RgMqE-2DW7GWsQAB0YgTS_TP7k/export?format=csv&gid=838976830";
    private static final String PETROL_URL =
            "https://docs.google.com/spreadsheets/d/1pUPeCiR0DFJtwC7r0RgMqE-2DW7GWsQAB0YgTS_TP7k/export?format=csv&gid=2068890812";
    private final String fileLocation = "src/main/resources/fuel-prices/";
    private final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private final ObjectMapper om = new ObjectMapper();
    private final InfoLogger infoLogger = new InfoLogger();
    private final ErrorLogger errorLogger = new ErrorLogger();


    public FuelPriceService() {
    }

    public void run() {
        totalPriceList.clear();
        createDieselPriceFile();
        createPetrolPriceFile();
        readDieselPricesFromCSV();
        readPetrolPricesFromCSV();
        writePricesToJsonFile();
    }

    private void createDieselPriceFile() {
        infoLogger.log("Creating diesel price file...");
        try {
            Optional<String> e = Optional.of(Jsoup.connect(DIESEL_URL).ignoreContentType(true).execute().body());
            File file = new File("src/main/resources/fuel-prices/diesel_" + today + ".csv");
            FileWriter fw = new FileWriter(file, false);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (e.isPresent()) {
                om.writeValue(fw, e.get());
            } else {
                om.writeValue(fw, "No diesel prices were found!\n");
            }
        } catch (IOException e) {
            errorLogger.log("Exception while creating Diesel csv file: " + e.getMessage());
        }
    }

    private void createPetrolPriceFile() {
        infoLogger.log("Creating petrol price file...");
        try {
            Optional<String> e = Optional.of(Jsoup.connect(PETROL_URL).ignoreContentType(true).execute().body());
            File file = new File("src/main/resources/fuel-prices/petrol_" + today + ".csv");
            FileWriter fw = new FileWriter(file, false);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (e.isPresent()) {
                om.writeValue(fw, e.get());
            } else {
                om.writeValue(fw, "No petrol prices were found!\n");
            }
        } catch (IOException e) {
            errorLogger.log("Exception while creating Petrol csv file: " + e.getMessage());
        }
    }

    private void readDieselPricesFromCSV() {
        List<FuelPrice> fuelPrices = new ArrayList<>();
        try {
            String filePrices = om.readValue(
                    new File(fileLocation + "diesel_" + today + ".csv"), new TypeReference<String>() {
                    }
            );
            totalPriceList.addAll(generatePriceList(filePrices, fuelPrices, "Diesel"));
        } catch (Exception e) {
            errorLogger.log("Exception while reading diesel prices file: " + e.getMessage());
        }
    }

    private void readPetrolPricesFromCSV() {
        List<FuelPrice> fuelPrices = new ArrayList<>();
        try {
            String filePrices = om.readValue(
                    new File(fileLocation + "petrol_" + today + ".csv"), new TypeReference<String>() {
                    }
            );
            totalPriceList.addAll(generatePriceList(filePrices, fuelPrices, "Petrol"));
        } catch (Exception e) {
            errorLogger.log("Exception while reading petrol prices file: " + e.getMessage());
        }
    }

    private void writePricesToJsonFile() {
        try {
            File jsonFile = new File(fileLocation + "all_prices_" + today + ".json");
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
            }
            om.writeValue(jsonFile, totalPriceList);
            infoLogger.log("Written " + totalPriceList.size() + " fuel price entries to JSON file.");
        } catch (IOException e) {
            errorLogger.log("Exception when writing price to JSON File: " + e.getMessage());
        }
    }

    private List<FuelPrice> generatePriceList(String filePrices, List<FuelPrice> fuelPrices, String fuelType) {
        FuelPrice fp;
        for (String line : filePrices.split("\\r\\n")) {
            if (!line.contains(",,")) {
                fp = new FuelPrice();
                fp.setBrand(line.split(",")[0]);
                fp.setFuelType(fuelType);
                fp.setPrice(line.split(",")[1]);
                fp.setDateUpdated(line.split(",")[3]);
                fuelPrices.add(fp);
            }
        }
        infoLogger.log("Generated " + fuelType + " price list with " + fuelPrices.size() + " entries.");
        return fuelPrices;
    }

    public List<FuelPrice> retriveJSONFile() {
        infoLogger.log("Retrieving fuel price JSON file...");
        SortedSet<FuelPrice> fuelPrices = new TreeSet<>(BRAND_SORT_COMPARATOR);
        try {
            List<FuelPrice> fromJson = om.readValue(
                    new File(fileLocation + "all_prices_" + today + ".json"),
                    new TypeReference<List<FuelPrice>>() {
                    }
            );
            fuelPrices.addAll(fromJson);
        } catch (Exception e) {
            errorLogger.log("Exception while retrieving fuel price JSON file: " + e.getMessage());
        }
        return filterBrands(fuelPrices);
    }

    private List<FuelPrice> filterBrands(SortedSet<FuelPrice> fuelPrices) {
        List<FuelPrice> filteredFuelPrices = new ArrayList<>();
        List<String> keepBrands = new ArrayList<>();
        keepBrands.add("asda");
        keepBrands.add("BP");
        keepBrands.add("esso");
        keepBrands.add("morrisons");
        keepBrands.add("sainsburys");
        keepBrands.add("shell");
        keepBrands.add("tesco");
        for (FuelPrice fp : fuelPrices) {
            if (keepBrands.contains(fp.getBrand())) {
                String capitalizedBrand = fp.getBrand().substring(0, 1).toUpperCase() + fp.getBrand().substring(1).toLowerCase();
                fp.setBrand(capitalizedBrand);
                filteredFuelPrices.add(fp);
            }
        }
        return filteredFuelPrices;
    }
}

