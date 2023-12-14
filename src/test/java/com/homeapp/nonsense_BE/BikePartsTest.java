package com.homeapp.nonsense_BE;

import com.homeapp.nonsense_BE.models.bike.*;
import com.homeapp.nonsense_BE.models.bike.Enums.BrakeType;
import com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType;
import com.homeapp.nonsense_BE.services.BikePartsService;
import com.homeapp.nonsense_BE.services.FullBikeService;
import com.homeapp.nonsense_BE.services.OptionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BikePartsTest {

    @Autowired
    private FullBikeService fullBikeService;

    @Autowired
    private BikePartsService bikePartsService;
    @Autowired
    private OptionsService optionsService;

    private static boolean isSetupDone = false;

    @BeforeEach
    public void setup() {
        if (!isSetupDone) {
            fullBikeService.deleteAllBikes();
            Frame frame10 = new Frame(GRAVEL, true, false, true);
            FullBike bike10 = new FullBike("Gravel", frame10, HYDRAULIC_DISC, SHIMANO, FLARE, 1L, 11L, STI);
            bike10.setWheelPreference("Expensive");
            fullBikeService.create(bike10);
            Frame frame = new Frame(GRAVEL, true, false, true);
            FullBike bike = new FullBike("bike", frame, MECHANICAL_DISC, SHIMANO, DROPS, 1L, 11L, STI);
            bike.setWheelPreference("Cheap");
            fullBikeService.create(bike);
            Frame frame1 = new Frame(ROAD, false, true, true);
            FullBike bike1 = new FullBike("bike1", frame1, RIM, SHIMANO, DROPS, 2L, 10L, STI);
            bike1.setWheelPreference("Expensive");
            fullBikeService.create(bike1);
            Frame frame2 = new Frame(SINGLE_SPEED, false, false, false);
            FullBike bike2 = new FullBike("bike2", frame2, NOT_REQUIRED, SHIMANO, BULLHORNS, 1L, 1L, NONE);
            bike2.setWheelPreference("Expensive");
            fullBikeService.create(bike2);
            Frame frame3 = new Frame(ROAD, true, true, true);
            FullBike bike3 = new FullBike("bike3", frame3, HYDRAULIC_DISC, SHIMANO, DROPS, 2L, 12L, STI);
            bike3.setWheelPreference("Cheap");
            fullBikeService.create(bike3);
            Frame frame4 = new Frame(SINGLE_SPEED, false, false, false);
            FullBike bike4 = new FullBike("bike4", frame4, RIM, SHIMANO, FLAT, 1L, 1L, NONE);
            bike4.setWheelPreference("Cheap");
            fullBikeService.create(bike4);
            Frame frame5 = new Frame(TOUR, true, true, true);
            FullBike bike5 = new FullBike("bike5", frame5, MECHANICAL_DISC, SHIMANO, FLARE, 3L, 10L, STI);
            bike5.setWheelPreference("Cheap");
            fullBikeService.create(bike5);
            Frame frame6 = new Frame(TOUR, true, true, true);
            FullBike bike6 = new FullBike("bike6", frame6, MECHANICAL_DISC, SHIMANO, FLAT, 1L, 10L, TRIGGER);
            bike6.setWheelPreference("Cheap");
            fullBikeService.create(bike6);
            Frame frame7 = new Frame(TOUR, true, true, true);
            FullBike bike7 = new FullBike("bike7", frame7, HYDRAULIC_DISC, SHIMANO, FLAT, 1L, 11L, TRIGGER);
            bike7.setWheelPreference("Expensive");
            fullBikeService.create(bike7);
            Frame frame8 = new Frame(SINGLE_SPEED, true, false, false);
            FullBike bike8 = new FullBike("bike8", frame8, MECHANICAL_DISC, SHIMANO, DROPS, 1L, 1L, NONE);
            bike8.setWheelPreference("Expensive");
            fullBikeService.create(bike8);
            isSetupDone = true;
        }
    }

    @Test
    public void test_That_All_SINGLE_SPEED_Options_Work() {
        int numberOfLoops = 1;
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike b = fullBikeService.startNewBike();
        b.getFrame().setFrameStyle(SINGLE_SPEED);
        CombinedData cd = new CombinedData();
        cd.setBike(b);
        cd.setOptions(o);
        o = optionsService.updateOptions(cd);
        for (String bt : o.getBrakeStyles()) {
            b.setBrakeType(BrakeType.fromName(bt));
            for (String ht : o.getBarStyles()) {
                b.setHandleBarType(HandleBarType.fromName(ht));
                for (Long fg : o.getNumberOfFrontGears()) {
                    b.setNumberOfFrontGears(fg);
                    for (Long rg : o.getNumberOfRearGears()) {
                        b.setNumberOfRearGears(rg);
                        for (String wp : o.getWheelPreference()) {
                            b.setWheelPreference(wp);
                            fullBikeService.setBike(b);
                            BikeParts parts = bikePartsService.getBikePartsForBike();
                            BigDecimal bikePrice = parts.getTotalBikePrice();
                            assertNotNull(bikePrice);
                            numberOfLoops = numberOfLoops + 1;
                            System.out.println(numberOfLoops);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_That_All_ROAD_Options_Work() {
        int numberOfLoops = 1;
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike b = fullBikeService.startNewBike();
        b.getFrame().setFrameStyle(ROAD);
        CombinedData cd = new CombinedData();
        cd.setBike(b);
        cd.setOptions(o);
        o = optionsService.updateOptions(cd);
        for (String bt : o.getBrakeStyles()) {
            b.setBrakeType(BrakeType.fromName(bt));
            for (String ht : o.getBarStyles()) {
                b.setHandleBarType(HandleBarType.fromName(ht));
                for (Long fg : o.getNumberOfFrontGears()) {
                    b.setNumberOfFrontGears(fg);
                    for (Long rg : o.getNumberOfRearGears()) {
                        b.setNumberOfRearGears(rg);
                        for (String wp : o.getWheelPreference()) {
                            b.setWheelPreference(wp);
                            fullBikeService.setBike(b);
                            BikeParts parts = bikePartsService.getBikePartsForBike();
                            BigDecimal bikePrice = parts.getTotalBikePrice();
                            assertNotNull(bikePrice);
                            numberOfLoops = numberOfLoops + 1;
                            System.out.println(numberOfLoops);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_That_All_GRAVEL_Options_Work() {
        int numberOfLoops = 1;
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike b = fullBikeService.startNewBike();
        b.getFrame().setFrameStyle(GRAVEL);
        CombinedData cd = new CombinedData();
        cd.setBike(b);
        cd.setOptions(o);
        o = optionsService.updateOptions(cd);
        for (String bt : o.getBrakeStyles()) {
            b.setBrakeType(BrakeType.fromName(bt));
            for (String ht : o.getBarStyles()) {
                b.setHandleBarType(HandleBarType.fromName(ht));
                for (Long fg : o.getNumberOfFrontGears()) {
                    b.setNumberOfFrontGears(fg);
                    for (Long rg : o.getNumberOfRearGears()) {
                        b.setNumberOfRearGears(rg);
                        for (String wp : o.getWheelPreference()) {
                            b.setWheelPreference(wp);
                            fullBikeService.setBike(b);
                            BikeParts parts = bikePartsService.getBikePartsForBike();
                            BigDecimal bikePrice = parts.getTotalBikePrice();
                            assertNotNull(bikePrice);
                            numberOfLoops = numberOfLoops + 1;
                            System.out.println(numberOfLoops);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_That_All_TOUR_Options_Work() {
        int numberOfLoops = 1;
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike b = fullBikeService.startNewBike();
        b.getFrame().setFrameStyle(TOUR);
        CombinedData cd = new CombinedData();
        cd.setBike(b);
        cd.setOptions(o);
        o = optionsService.updateOptions(cd);
        for (String bt : o.getBrakeStyles()) {
            b.setBrakeType(BrakeType.fromName(bt));
            for (String ht : o.getBarStyles()) {
                b.setHandleBarType(HandleBarType.fromName(ht));
                for (Long fg : o.getNumberOfFrontGears()) {
                    b.setNumberOfFrontGears(fg);
                    for (Long rg : o.getNumberOfRearGears()) {
                        b.setNumberOfRearGears(rg);
                        for (String wp : o.getWheelPreference()) {
                            b.setWheelPreference(wp);
                            fullBikeService.setBike(b);
                            BikeParts parts = bikePartsService.getBikePartsForBike();
                            BigDecimal bikePrice = parts.getTotalBikePrice();
                            assertNotNull(bikePrice);
                            numberOfLoops = numberOfLoops + 1;
                            System.out.println(numberOfLoops);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_That_The_Parts_Array_is_Populated() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        assertTrue(parts.getListOfParts().size() > 1);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Heaps1() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike1");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice > 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps2() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike2");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps3() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike3");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps4() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike4");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Heaps5() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike5");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        System.out.println(parts.getTotalPriceAsString());
        assertTrue(bikePrice > 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Heaps6() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike6");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice > 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Heaps7() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike7");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice > 1500);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps8() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike8");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        for (Part p : parts.getListOfParts()) {
            System.out.println(p.toString());
        }
        assertTrue(bikePrice < 1500);
    }

    @Test
    public void test_That_Gravel_Bike_Has_Price_With_Two_decimals() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("Gravel");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        String bikePrice = parts.getTotalPriceAsString();
        assertEquals("£2,085.90", bikePrice);
    }

    @Test
    public void test_That_Different_Bikes_Get_Different_Price() {
        FullBike bike1Before = fullBikeService.getBikeUsingName("bike");
        fullBikeService.setBike(bike1Before);
        BikeParts parts1 = bikePartsService.getBikePartsForBike();
        FullBike bike1After = fullBikeService.getBike();
        long bike1Price = parts1.getTotalBikePrice().longValue();
        FullBike bike2Before = fullBikeService.getBikeUsingName("bike3");
        fullBikeService.setBike(bike2Before);
        BikeParts parts2 = bikePartsService.getBikePartsForBike();
        long bike2Price = parts2.getTotalBikePrice().longValue();
        assertNotSame(bike1Price, bike2Price);
    }

    @Test
    public void test_That_Bike_Part_Details_Not_Null() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike");
        fullBikeService.setBike(bikeBefore);
        List<Part> bParts = bikePartsService.getBikePartsForBike().getListOfParts();
        for (Part part : bParts) {
            assertNotNull(part);
            assertNotNull(part.getName());
            assertNotNull(part.getPrice());
            assertNotNull(part.getLink());
        }
    }

    @Test
    public void test_That_Bike5_Part_Details_Not_Null() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike5");
        fullBikeService.setBike(bikeBefore);
        List<Part> bParts = bikePartsService.getBikePartsForBike().getListOfParts();
        for (Part part : bParts) {
            assertNotNull(part);
            assertNotNull(part.getName());
            assertNotNull(part.getPrice());
            assertNotNull(part.getLink());
        }
    }
}