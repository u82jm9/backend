package com.homeapp.nonsense_BE;

import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.BULLHORNS;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.DROPS;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.NONE;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class FullBikeTest {

    private static boolean isSetupDone = false;
    @Autowired
    private FullBikeService fullBikeService;

    @BeforeEach
    public void setup() {
        if (!isSetupDone) {
            Frame frame = new Frame(GRAVEL, true, false, true);
            FullBike bike = new FullBike("bike", frame, MECHANICAL_DISC, SHIMANO, DROPS, 1L, 11L, STI);
            fullBikeService.create(bike);
            Frame frame1 = new Frame(ROAD, false, true, true);
            FullBike bike1 = new FullBike("bike1", frame1, RIM, SHIMANO, DROPS, 2L, 10L, STI);
            fullBikeService.create(bike1);
            Frame frame2 = new Frame(SINGLE_SPEED, false, false, false);
            FullBike bike2 = new FullBike("bike2", frame2, NOT_REQUIRED, SHIMANO, BULLHORNS, 1L, 1L, NONE);
            fullBikeService.create(bike2);
            Frame frame3 = new Frame(ROAD, true, true, true);
            FullBike bike3 = new FullBike("bike3", frame3, HYDRAULIC_DISC, SHIMANO, DROPS, 2L, 12L, STI);
            fullBikeService.create(bike3);
            isSetupDone = true;
        }
    }

    @Test
    public void test_That_a_bike_can_be_deleted_from_DB() {
        int bikesOnDB = fullBikeService.getAllFullBikes().size();
        FullBike bike = fullBikeService.getBikeUsingName("bike1");
        fullBikeService.deleteBike(bike.getFullBikeId());
        assertTrue(fullBikeService.getAllFullBikes().size() < bikesOnDB);
    }

    @Test
    public void test_That_a_list_of_bikes_is_returned_from_DB() {
        assertTrue(fullBikeService.getAllFullBikes().size() > 0);
    }

    @Test
    public void test_That_a_Full_Bike_can_be_Created() {
        int numberOfBikesBefore = fullBikeService.getAllFullBikes().size();
        Frame frame = new Frame(ROAD, false, true, true);
        FullBike testBike = new FullBike("test Bike", frame, RIM, SHIMANO, DROPS, 2L, 10L, STI);
        fullBikeService.create(testBike);
        int numberOfBikesAfter = fullBikeService.getAllFullBikes().size();
        assertTrue(numberOfBikesAfter > numberOfBikesBefore);
    }

    @Test
    public void test_That_a_Full_Bike_can_be_Updated() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike");
        bikeBefore.setNumberOfRearGears(9);
        fullBikeService.updateBike(bikeBefore);
        FullBike bikeAfter = fullBikeService.getBikeUsingName("bike");
        assertNotSame(bikeAfter, bikeBefore);
    }
}