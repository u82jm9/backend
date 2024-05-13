package com.homeapp.backend;

import com.homeapp.backend.models.bike.CombinedData;
import com.homeapp.backend.models.bike.FullBike;
import com.homeapp.backend.models.bike.Options;
import com.homeapp.backend.services.FullBikeService;
import com.homeapp.backend.services.OptionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.homeapp.backend.models.bike.Enums.BrakeType.*;
import static com.homeapp.backend.models.bike.Enums.FrameStyle.*;
import static com.homeapp.backend.models.bike.Enums.HandleBarType.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Options tests.
 */
@SpringBootTest
public class OptionsTest {

    @Autowired
    private OptionsService optionsService;
    @Autowired
    private FullBikeService fullBikeService;

    /**
     * Test that start new bike returns correctly.
     */
    @Test
    public void test_That_Start_New_Bike_Returns_Correctly() {
        Options options = optionsService.startNewBike();
        assertEquals(options.getFrameStyles().size(), 4);
        assertTrue(options.isShowFrameSizes());
        assertTrue(options.isShowGroupSetBrand());
        assertEquals(options.getGroupsetBrand().size(), 1);
    }

    /**
     * Test that single speed gets no gear options.
     */
    @Test
    public void test_That_Single_Speed_Gets_No_Gear_Options() {
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike bike = fullBikeService.startNewBike();
        bike.getFrame().setFrameStyle(SINGLE_SPEED);
        CombinedData cd = new CombinedData();
        cd.setBike(bike);
        cd.setOptions(o);
        Options options = optionsService.updateOptions(cd);
        assertFalse(options.isShowFrontGears());
        assertFalse(options.isShowRearGears());
        assertTrue(options.isShowBarStyles());
        assertTrue(options.isShowBrakeStyles());
        assertTrue(options.getBrakeStyles().contains(RIM.getName()));
        assertTrue(options.getBrakeStyles().contains(NOT_REQUIRED.getName()));
        assertEquals(options.getBrakeStyles().size(), 2);
        assertTrue(options.getBarStyles().contains(BULLHORNS.getName()));
        assertTrue(options.getBarStyles().contains(FLAT.getName()));
        assertTrue(options.getBarStyles().contains(DROPS.getName()));
        assertEquals(options.getBarStyles().size(), 3);
    }

    /**
     * Test that road gets right options.
     */
    @Test
    public void test_That_Road_Gets_Right_Options() {
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike bike = fullBikeService.startNewBike();
        bike.getFrame().setFrameStyle(ROAD);
        CombinedData cd = new CombinedData();
        cd.setBike(bike);
        cd.setOptions(o);
        Options options = optionsService.updateOptions(cd);
        assertTrue(options.isShowFrontGears());
        assertTrue(options.isShowRearGears());
        assertTrue(options.isShowBarStyles());
        assertTrue(options.isShowWheelPreference());
        assertTrue(options.getBrakeStyles().contains(MECHANICAL_DISC.getName()));
        assertTrue(options.getBrakeStyles().contains(HYDRAULIC_DISC.getName()));
        assertEquals(options.getBrakeStyles().size(), 3);
        assertTrue(options.getBarStyles().contains(DROPS.getName()));
        assertFalse(options.getBarStyles().contains(FLARE.getName()));
        assertFalse(options.getBarStyles().contains(FLAT.getName()));
        assertEquals(options.getBarStyles().size(), 1);
        assertTrue(options.getNumberOfRearGears().contains(10L));
        assertTrue(options.getNumberOfRearGears().contains(11L));
        assertTrue(options.getNumberOfRearGears().contains(12L));
        assertEquals(options.getNumberOfRearGears().size(), 3);
        assertTrue(options.getNumberOfFrontGears().contains(2L));
        assertEquals(options.getNumberOfFrontGears().size(), 1);
        assertTrue(options.getWheelPreference().contains("Cheap"));
        assertTrue(options.getWheelPreference().contains("Expensive"));
    }

    /**
     * Test that gravel gets right options.
     */
    @Test
    public void test_That_Gravel_Gets_Right_Options() {
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike bike = fullBikeService.startNewBike();
        bike.getFrame().setFrameStyle(GRAVEL);
        CombinedData cd = new CombinedData();
        cd.setBike(bike);
        cd.setOptions(o);
        Options options = optionsService.updateOptions(cd);
        assertTrue(options.isShowFrontGears());
        assertTrue(options.isShowRearGears());
        assertTrue(options.isShowBarStyles());
        assertTrue(options.isShowWheelPreference());
        assertTrue(options.getBrakeStyles().contains(RIM.getName()));
        assertTrue(options.getBrakeStyles().contains(MECHANICAL_DISC.getName()));
        assertTrue(options.getBrakeStyles().contains(HYDRAULIC_DISC.getName()));
        assertEquals(options.getBrakeStyles().size(), 3);
        assertTrue(options.getBarStyles().contains(DROPS.getName()));
        assertTrue(options.getBarStyles().contains(FLARE.getName()));
        assertEquals(options.getBarStyles().size(), 2);
        assertTrue(options.getNumberOfRearGears().contains(9L));
        assertTrue(options.getNumberOfRearGears().contains(10L));
        assertTrue(options.getNumberOfRearGears().contains(11L));
        assertEquals(options.getNumberOfRearGears().size(), 3);
        assertTrue(options.getNumberOfFrontGears().contains(2L));
        assertTrue(options.getNumberOfFrontGears().contains(1L));
        assertEquals(options.getNumberOfFrontGears().size(), 2);
        assertTrue(options.getWheelPreference().contains("Cheap"));
        assertTrue(options.getWheelPreference().contains("Expensive"));
    }

    /**
     * Test that tour gets right options.
     */
    @Test
    public void test_That_Tour_Gets_Right_Options() {
        Options o = optionsService.startNewBike();
        o.setShowFrameStyles(false);
        FullBike bike = fullBikeService.startNewBike();
        bike.getFrame().setFrameStyle(TOUR);
        CombinedData cd = new CombinedData();
        cd.setBike(bike);
        cd.setOptions(o);
        Options options = optionsService.updateOptions(cd);
        assertTrue(options.isShowFrontGears());
        assertTrue(options.isShowRearGears());
        assertTrue(options.isShowBarStyles());
        assertTrue(options.isShowWheelPreference());
        assertTrue(options.getBrakeStyles().contains(RIM.getName()));
        assertTrue(options.getBrakeStyles().contains(MECHANICAL_DISC.getName()));
        assertTrue(options.getBrakeStyles().contains(HYDRAULIC_DISC.getName()));
        assertEquals(options.getBrakeStyles().size(), 3);
        assertTrue(options.getBarStyles().contains(DROPS.getName()));
        assertTrue(options.getBarStyles().contains(FLARE.getName()));
        assertTrue(options.getBarStyles().contains(FLAT.getName()));
        assertEquals(options.getBarStyles().size(), 3);
        assertTrue(options.getNumberOfRearGears().contains(9L));
        assertTrue(options.getNumberOfRearGears().contains(10L));
        assertTrue(options.getNumberOfRearGears().contains(11L));
        assertEquals(options.getNumberOfRearGears().size(), 3);
        assertTrue(options.getNumberOfFrontGears().contains(3L));
        assertTrue(options.getNumberOfFrontGears().contains(2L));
        assertEquals(options.getNumberOfFrontGears().size(), 2);
        assertTrue(options.getWheelPreference().contains("Cheap"));
        assertTrue(options.getWheelPreference().contains("Expensive"));
    }
}