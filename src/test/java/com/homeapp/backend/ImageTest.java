package com.homeapp.backend;

import com.homeapp.backend.models.bike.Frame;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Image;
import com.homeapp.backend.services.FullBikeService;
import com.homeapp.backend.services.ImageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.homeapp.backend.models.bike.Enums.BrakeType.*;
import static com.homeapp.backend.models.bike.Enums.FrameStyle.*;
import static com.homeapp.backend.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.backend.models.bike.Enums.HandleBarType.*;
import static com.homeapp.backend.models.bike.Enums.ShifterStyle.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Image test.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ImageTest {
    private static boolean isSetupDone = false;

    @Autowired
    private ImageService imageService;
    @Autowired
    private FullBikeService fullBikeService;

    /**
     * Sets up testing suite.
     * Uses a boolean to ensure test suite is only set once.
     * First all bikes on file are deleted, then new specific test bikes are added. This is to make testing more rigid and predictable.
     */
    @BeforeEach
    public void setup() {
        if (!isSetupDone) {
            fullBikeService.deleteAllBikes();
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
            bike2.setWheelPreference("Cheap");
            fullBikeService.create(bike2);
            Frame frame3 = new Frame(ROAD, true, true, true);
            FullBike bike3 = new FullBike("bike3", frame3, HYDRAULIC_DISC, SHIMANO, DROPS, 2L, 12L, STI);
            bike3.setWheelPreference("Expensive");
            fullBikeService.create(bike3);
            Frame frame4 = new Frame(TOUR, false, true, true);
            FullBike bike4 = new FullBike("bike4", frame4, RIM, SHIMANO, FLAT, 3L, 11L, TRIGGER);
            bike4.setWheelPreference("Expensive");
            fullBikeService.create(bike4);
            isSetupDone = true;
        }
    }

    /**
     * Clearup after tests complete.
     * Replaces the bikes on file from the back-up version, to remove all testing impact on the proper FE bikes.
     */
    @AfterAll
    public void clearup() {
        fullBikeService.reloadBikesFromBackup();
    }

    /**
     * Test that list of images is returned.
     */
    @Test
    public void test_That_List_of_Images_is_Returned() {
        FullBike bike = fullBikeService.getBikeUsingName("bike1").get();
        assertEquals(imageService.getImages(bike).size(), 10);
    }

    /**
     * Test that one by bike gets less images.
     */
    @Test
    public void test_That_One_By_Bike_Gets_Less_Images() {
        FullBike bike = fullBikeService.getBikeUsingName("bike").get();
        FullBike bike1 = fullBikeService.getBikeUsingName("bike1").get();
        assertTrue(imageService.getImages(bike).size() < imageService.getImages(bike1).size());
    }

    /**
     * Test that different bikes get different lists.
     */
    @Test
    public void test_That_Different_Bikes_get_different_lists() {
        List<Image> images1 = imageService.getImages(fullBikeService.getBikeUsingName("bike1").get());
        List<Image> images2 = imageService.getImages(fullBikeService.getBikeUsingName("bike2").get());
        assertNotEquals(images1, images2);
    }

    /**
     * Test that images are not null.
     */
    @Test
    public void test_That_Images_Are_Not_Null() {
        FullBike bike = fullBikeService.getBikeUsingName("bike1").get();
        List<Image> images = imageService.getImages(bike);
        for (Image image : images) {
            assertNotNull(image.getComponent());
            assertNotNull(image.getSrc());
            assertNotNull(image.getAltText());
            assertNotNull(image);
        }
    }
}