package com.homeapp.one.demo;

import com.homeapp.one.demo.models.Frame;
import com.homeapp.one.demo.models.FrontGears;
import com.homeapp.one.demo.models.FullBike;
import com.homeapp.one.demo.models.RearGears;
import com.homeapp.one.demo.services.FullBikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.homeapp.one.demo.models.Enums.BrakeType.HYDRAULIC_DISC;
import static com.homeapp.one.demo.models.Enums.BrakeType.RIM;
import static com.homeapp.one.demo.models.Enums.FrameStyle.GRAVEL;
import static com.homeapp.one.demo.models.Enums.FrameStyle.ROAD;
import static com.homeapp.one.demo.models.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.one.demo.models.Enums.GroupsetBrand.SRAM;
import static com.homeapp.one.demo.models.Enums.HandleBarType.DROPS;
import static com.homeapp.one.demo.models.Enums.ShifterStyle.STI;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FullBikeTest {

    @Autowired
    private FullBikeService fullBikeService;

    @BeforeEach
    private void setup() {
        Frame frame = new Frame(GRAVEL, true, false, true, STI);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike bike = new FullBike("bike", frame, HYDRAULIC_DISC, SRAM, DROPS, frontGears, rearGears);
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
        Frame frame = new Frame(ROAD, false, true, true, STI);
        FrontGears frontGears = new FrontGears(2);
        RearGears rearGears = new RearGears(10);
        FullBike testBike = new FullBike("test Bike", frame, RIM, SHIMANO, DROPS, frontGears, rearGears);
        fullBikeService.create(testBike);
        Integer numberOfBikesAfter = fullBikeService.getAllFullBikes().size();
        assertTrue(numberOfBikesAfter > numberOfBikesBefore);
    }
}