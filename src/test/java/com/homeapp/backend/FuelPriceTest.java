package com.homeapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.backend.models.FuelPrice;
import com.homeapp.backend.services.FuelPriceService;;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The Fuel Price tests.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class FuelPriceTest {

    @Autowired
    private FuelPriceService fuelPriceService;

    private String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    /**
     * Sets up testing suite.
     * Uses a boolean to ensure test suite is only set once.
     * New specific test Notes are added. This is to make testing more rigid and predictable.
     */
    @BeforeAll
    public void setup() throws IOException {
        fuelPriceService.run();
    }

    @Test
    public void test_csv_files_created() {
        File dieselcsvFile = new File("src/main/resources/fuel-prices/diesel_" + today + ".csv");
        File petrolcsvFile = new File("src/main/resources/fuel-prices/petrol_" + today + ".csv");
        assertTrue(dieselcsvFile.exists());
        assertTrue(petrolcsvFile.exists());
    }

    @Test
    public void test_json_file_created() {
        File jsonFile = new File("src/main/resources/fuel-prices/fuel_" + today + ".json");
        assertTrue(jsonFile.exists());
    }

    @Test
    public void test_json_file_contains_Fuel_Prices() throws IOException {
        ObjectMapper om = new ObjectMapper();
        List<FuelPrice> filePrices = om.readValue(
                new File("src/main/resources/fuel-prices/fuel_" + today + ".json"), new TypeReference<List<FuelPrice>>() {
                }
        );
        assertTrue(filePrices.size() > 10);
    }
}