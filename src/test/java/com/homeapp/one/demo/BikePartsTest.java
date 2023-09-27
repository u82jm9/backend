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

import java.math.BigDecimal;

import static com.homeapp.one.demo.models.bike.Enums.BrakeType.*;
import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.*;
import static com.homeapp.one.demo.models.bike.Enums.GroupsetBrand.*;
import static com.homeapp.one.demo.models.bike.Enums.HandleBarType.*;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.NONE;
import static com.homeapp.one.demo.models.bike.Enums.ShifterStyle.STI;
import static com.homeapp.one.demo.models.bike.Enums.ShimanoGroupSet.TIAGRA;
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
    private void setup() {
        Frame frame = new Frame(GRAVEL, true, false, true);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike bike = new FullBike("bike", null, frame, HYDRAULIC_DISC, SRAM, DROPS, frontGears, rearGears, STI);
        fullBikeService.create(bike);
        Frame frame1 = new Frame(ROAD, false, true, true);
        FrontGears frontGears1 = new FrontGears(2);
        RearGears rearGears1 = new RearGears(10);
        FullBike bike1 = new FullBike("bike1", null, frame1, RIM, SRAM, DROPS, frontGears1, rearGears1, STI);
        fullBikeService.create(bike1);
        Frame frame2 = new Frame(SINGLE_SPEED, false, false, false);
        FrontGears frontGears2 = new FrontGears(1);
        frontGears2.setShimanoGroupSet(TIAGRA);
        RearGears rearGears2 = new RearGears(1);
        rearGears2.setShimanoGroupSet(TIAGRA);
        FullBike bike2 = new FullBike("bike2", null, frame2, NOT_REQUIRED, SHIMANO, BULLHORNS, frontGears2, rearGears2, NONE);
        fullBikeService.create(bike2);
        Frame frame3 = new Frame(ROAD, true, true, true);
        FrontGears frontGears3 = new FrontGears(2);
        RearGears rearGears3 = new RearGears(12);
        FullBike bike3 = new FullBike("bike3", null, frame3, HYDRAULIC_DISC, CAMPAGNOLO, DROPS, frontGears3, rearGears3, STI);
        fullBikeService.create(bike3);
        Frame frame4 = new Frame(SINGLE_SPEED, false, false, false);
        FrontGears frontGears4 = new FrontGears(1);
        RearGears rearGears4 = new RearGears(1);
        FullBike bike4 = new FullBike("bike4", null, frame4, RIM, SHIMANO, FLAT, frontGears4, rearGears4, NONE);
        fullBikeService.create(bike4);
        Frame frame5 = new Frame(TOUR, true, true, true);
        FrontGears frontGears5 = new FrontGears(3);
        RearGears rearGears5 = new RearGears(11);
        FullBike bike5 = new FullBike("bike5", null, frame5, MECHANICAL_DISC, CAMPAGNOLO, FLARE, frontGears5, rearGears5, STI);
        fullBikeService.create(bike5);
    }

    @Test
    public void test_That_The_Links_Array_is_Populated() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike");
        bikePartsService.getBikePartsForBike(bike);
        assertTrue(bike.getBikeParts().getWeblinks().size() > 1);
    }

    @Test
    public void test_That_Different_Bikes_Get_Different_Frame_Names() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike");
        bikePartsService.getBikePartsForBike(bike);
        String frameName = bike.getBikeParts().getFrameName();
        FullBike bike1 = fullBikeService.getBikeUsingName("bike1");
        bikePartsService.getBikePartsForBike(bike1);
        String frameName1 = bike1.getBikeParts().getFrameName();
        assertNotEquals(frameName, frameName1);
    }

    @Test
    public void test_That_A_Frame_Name_Found_For_Gravel() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike");
        bikePartsService.getBikePartsForBike(bike);
        String frameName = bike.getBikeParts().getFrameName();
        assertEquals(frameName, "Dolan GXA 2020 Aluminium Gravel Frameset");
    }

    @Test
    public void test_That_A_Frame_Price_Found_For_Gravel() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike");
        bikePartsService.getBikePartsForBike(bike);
        BigDecimal framePrice = bike.getBikeParts().getFramePrice();
        assertEquals(framePrice, 599.99);
    }

    @Test
    public void test_That_A_Frame_Name_Found_For_Tour() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike5");
        bikePartsService.getBikePartsForBike(bike);
        String frameName = bike.getBikeParts().getFrameName();
        assertEquals(frameName, "Genesis Fugio Frameset");
    }

    @Test
    public void test_That_A_Frame_Price_Found_For_Tour() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike5");
        bikePartsService.getBikePartsForBike(bike);
        BigDecimal framePrice = bike.getBikeParts().getFramePrice();
        assertEquals(framePrice, 1199.99);
    }

    @Test
    public void test_That_A_Handlebar_Price_Found_For_Drops() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike");
        bikePartsService.getBikePartsForBike(bike);
        assertNotNull(bike.getBikeParts().getHandlebarPrice());
    }

    @Test
    public void test_That_A_Handlebar_Name_Found_For_Flare() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike2");
        bikePartsService.getBikePartsForBike(bike);
        String handlebarNameame = bike.getBikeParts().getHandlebarName();
        assertNotNull(handlebarNameame);
    }
}