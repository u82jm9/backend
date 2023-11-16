package com.homeapp.nonsense_BE;

import com.homeapp.nonsense_BE.models.bike.*;
import com.homeapp.nonsense_BE.services.BikePartsService;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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

    @BeforeEach
    public void setup() {
        Frame frame = new Frame(GRAVEL, true, false, true);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike bike = new FullBike("bike", null, frame, MECHANICAL_DISC, SHIMANO, DROPS, frontGears, rearGears, STI);
        fullBikeService.create(bike);
        Frame frame1 = new Frame(ROAD, false, true, true);
        FrontGears frontGears1 = new FrontGears(2);
        RearGears rearGears1 = new RearGears(10);
        FullBike bike1 = new FullBike("bike1", null, frame1, RIM, SHIMANO, DROPS, frontGears1, rearGears1, STI);
        fullBikeService.create(bike1);
        Frame frame2 = new Frame(SINGLE_SPEED, false, false, false);
        FrontGears frontGears2 = new FrontGears(1);
        RearGears rearGears2 = new RearGears(1);
        FullBike bike2 = new FullBike("bike2", null, frame2, NOT_REQUIRED, SHIMANO, BULLHORNS, frontGears2, rearGears2, NONE);
        fullBikeService.create(bike2);
        Frame frame3 = new Frame(ROAD, true, true, true);
        FrontGears frontGears3 = new FrontGears(2);
        RearGears rearGears3 = new RearGears(12);
        FullBike bike3 = new FullBike("bike3", null, frame3, HYDRAULIC_DISC, SHIMANO, DROPS, frontGears3, rearGears3, STI);
        fullBikeService.create(bike3);
        Frame frame4 = new Frame(SINGLE_SPEED, false, false, false);
        FrontGears frontGears4 = new FrontGears(1);
        RearGears rearGears4 = new RearGears(1);
        FullBike bike4 = new FullBike("bike4", null, frame4, RIM, SHIMANO, FLAT, frontGears4, rearGears4, NONE);
        fullBikeService.create(bike4);
        Frame frame5 = new Frame(TOUR, true, true, true);
        FrontGears frontGears5 = new FrontGears(3);
        RearGears rearGears5 = new RearGears(11);
        FullBike bike5 = new FullBike("bike5", null, frame5, MECHANICAL_DISC, SHIMANO, FLARE, frontGears5, rearGears5, TRIGGER);
        fullBikeService.create(bike5);
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
        assertTrue(bikePrice < 1000);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps1() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike1");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1000);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps2() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike2");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1000);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps3() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike3");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice < 1000);
    }

    @Test
    public void test_That_The_Full_Price_is_Cheaps4() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike4");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        System.out.println("Part: " + parts);
        System.out.println("Price: " + bikePrice);
        assertTrue(bikePrice < 1000);
    }

    @Test
    public void test_That_The_Full_Price_is_Heaps5() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike5");
        fullBikeService.setBike(bikeBefore);
        BikeParts parts = bikePartsService.getBikePartsForBike();
        long bikePrice = parts.getTotalBikePrice().longValue();
        assertTrue(bikePrice > 1000);
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