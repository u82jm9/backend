package com.homeapp.one.demo;

import com.homeapp.one.demo.models.bike.Frame;
import com.homeapp.one.demo.models.bike.FrontGears;
import com.homeapp.one.demo.models.bike.FullBike;
import com.homeapp.one.demo.models.bike.RearGears;
import com.homeapp.one.demo.services.BikePartsService;
import com.homeapp.one.demo.services.FullBikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.List;

import static com.homeapp.one.demo.models.bike.Enums.BrakeType.HYDRAULIC_DISC;
import static com.homeapp.one.demo.models.bike.Enums.BrakeType.RIM;
import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.GRAVEL;
import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.ROAD;
import static com.homeapp.one.demo.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.one.demo.models.bike.Enums.GroupsetBrand.SRAM;
import static com.homeapp.one.demo.models.bike.Enums.HandleBarType.DROPS;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.STI;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class FullBikeTest {

    @Autowired
    private FullBikeService fullBikeService;

    @Autowired
    private BikePartsService bikePartsService;

    @BeforeEach
    private void setup() {
        Frame frame = new Frame(GRAVEL, true, false, true);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike bike = new FullBike("bike", null, frame, HYDRAULIC_DISC, SRAM, DROPS, frontGears, rearGears, STI);
        fullBikeService.create(bike);
    }

    @Test
    public void test_That_a_list_of_bikes_is_returned_from_DB() {
        assertTrue(fullBikeService.getAllFullBikes().size() > 0);
    }

    @Test
    public void test_That_a_list_of_frame_Styles_is_returned_as_string() {
        List<String> names = fullBikeService.getAllFrameStyleNames();
        assertTrue(names.size() > 0);
    }

    @Test
    public void test_That_a_Full_Bike_can_be_created() {
        Integer numberOfBikesBefore = fullBikeService.getAllFullBikes().size();
        Frame frame = new Frame(ROAD, false, true, true);
        FrontGears frontGears = new FrontGears(2);
        RearGears rearGears = new RearGears(10);
        FullBike testBike = new FullBike("test Bike", null, frame, RIM, SHIMANO, DROPS, frontGears, rearGears, STI);
        fullBikeService.create(testBike);
        Integer numberOfBikesAfter = fullBikeService.getAllFullBikes().size();
        assertTrue(numberOfBikesAfter > numberOfBikesBefore);
    }

    @Test
    public void test_That_A_Complete_Bike_Can_Have_Parts_Found() throws Exception {
        FullBike b = fullBikeService.getAllFullBikes().stream().findFirst().get();
        bikePartsService.getBikePartsForBike(b);
        String frameName = b.getBikeParts().getFrameName();
        assertNotNull(frameName);
    }
}