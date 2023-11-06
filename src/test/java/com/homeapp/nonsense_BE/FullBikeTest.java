package com.homeapp.nonsense_BE;

import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FrontGears;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.RearGears;
import com.homeapp.nonsense_BE.services.FullBikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.HYDRAULIC_DISC;
import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.RIM;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.GRAVEL;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.ROAD;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SRAM;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.DROPS;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class FullBikeTest {

    @Autowired
    private FullBikeService fullBikeService;

    @BeforeEach
    public void setup() {
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
        int numberOfBikesBefore = fullBikeService.getAllFullBikes().size();
        Frame frame = new Frame(ROAD, false, true, true);
        FrontGears frontGears = new FrontGears(2);
        RearGears rearGears = new RearGears(10);
        FullBike testBike = new FullBike("test Bike", null, frame, RIM, SHIMANO, DROPS, frontGears, rearGears, STI);
        fullBikeService.create(testBike);
        int numberOfBikesAfter = fullBikeService.getAllFullBikes().size();
        assertTrue(numberOfBikesAfter > numberOfBikesBefore);
    }

    @Test
    public void test_That_a_Full_Bike_can_be_updated() {
        FullBike bikeBefore = fullBikeService.getBikeUsingName("bike");
        bikeBefore.getRearGears().setNumberOfGears(9);
        fullBikeService.updateBike(bikeBefore);
        FullBike bikeAfter = fullBikeService.getBikeUsingName("bike");
        assertNotSame(bikeAfter, bikeBefore);
    }
}