package com.homeapp.nonsense_BE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.controller.StickyNoteController;
import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FrontGears;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.RearGears;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.MECHANICAL_DISC;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.GRAVEL;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.DROPS;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StickyNoteController stickyNoteController;
    private MockMvc mockMvc;
    private MockHttpSession session;
    final static String TEST_API_URL = "/Test/";
    final static String STICKY_NOTE_URL = "/StickyNotes/";
    final static String FULL_BIKE_URL = "/FullBike/";
    final static String OPTIONS_URL = "/Options/";

    @BeforeEach
    public void setup() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
    }

    @AfterEach
    public void close() {
        this.session = null;
        this.mockMvc = null;
    }

    @Test
    public void test_That_Options_is_returned_with_Brands() throws Exception {
        this.mockMvc.perform(get(OPTIONS_URL + "startingNewBike"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_the_front_can_check_back_end_is_on() throws Exception {
        this.mockMvc.perform(get(TEST_API_URL + "IsThisThingOn"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_a_note_can_be_created() throws Exception {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Test Message", false);
        StickyNote note = new StickyNote("Controller test", map, false);
        this.mockMvc.perform(post(STICKY_NOTE_URL + "AddNote").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(note))).andExpect(status().isCreated());
    }

    @Test
    public void test_That_a_list_of_Notes_can_be_returned() throws Exception {
        this.mockMvc.perform(get(STICKY_NOTE_URL + "GetAll"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test_That_a_single_Note_can_be_deleted() throws Exception {
        this.mockMvc.perform(delete(STICKY_NOTE_URL + "DeleteNote/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_all_Notes_can_be_deleted() throws Exception {
        this.mockMvc.perform(delete(STICKY_NOTE_URL + "DeleteAllNotes"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_a_Note_can_be_edited() throws Exception {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Do it NOW!", false);
        StickyNote note = new StickyNote("Go for a run!", map, false);
        this.mockMvc.perform(post(STICKY_NOTE_URL + "EditNote").session(session).contentType("application/json")
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_a_list_of_Full_Bikes_can_be_returned() throws Exception {
        this.mockMvc.perform(get(FULL_BIKE_URL + "GetAll"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test_That_an_empty_Bikes_can_be_created() throws Exception {
        this.mockMvc.perform(get(FULL_BIKE_URL + "StartNewBike"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test_That_a_list_of_Frames_can_be_returned() throws Exception {
        this.mockMvc.perform(get(FULL_BIKE_URL + "GetFrames"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test_That_an_empty_bike_can_be_created() throws Exception {
        FullBike bike = new FullBike();
        this.mockMvc.perform(post(FULL_BIKE_URL + "AddFullBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(bike))).andExpect(status().isCreated());
    }

    @Test
    public void test_That_an_in_flight_bike_can_be_updated() throws Exception {
        FullBike bike = new FullBike();
        bike.setBikeName("Bike Update");
        this.mockMvc.perform(post(FULL_BIKE_URL + "UpdateBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(bike))).andExpect(status().isCreated());
    }

    @Test
    public void test_That_a_fully_defined_bike_can_be_created() throws Exception {
        Frame frame = new Frame(GRAVEL, false, true, true);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike testBike = new FullBike("testBike", null, frame, MECHANICAL_DISC, SHIMANO, DROPS, frontGears, rearGears, STI);
        this.mockMvc.perform(post(FULL_BIKE_URL + "AddFullBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(testBike))).andExpect(status().isCreated());
    }
}