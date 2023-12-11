package com.homeapp.nonsense_BE;

import com.homeapp.nonsense_BE.models.bike.*;
import com.homeapp.nonsense_BE.services.FullBikeService;
import com.homeapp.nonsense_BE.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.NONE;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageTest {
    private static boolean isSetupDone = false;

    @Autowired
    private ImageService imageService;
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
    public void test_That_Selection_of_image_is_returned() {
        FullBike bike = fullBikeService.getBikeUsingName("bike1");
        List<Image> images = imageService.getImages(bike);
        assertEquals(images.size(), 3);
    }
}