package com.homeapp.nonsense_BE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.controller.StickyNoteController;
import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FrontGears;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.RearGears;
import com.homeapp.nonsense_BE.models.note.DTOnote;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import com.homeapp.nonsense_BE.services.FullBikeService;
import com.homeapp.nonsense_BE.services.StickyNoteService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.GRAVEL;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.ROAD;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SRAM;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.DROPS;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StickyNoteController stickyNoteController;
    @Autowired
    private FullBikeService fullBikeService;
    @Autowired
    private StickyNoteService stickyNoteService;
    private MockMvc mockMvc;
    private MockHttpSession session;
    final static String TEST_API_URL = "/Test/";
    final static String STICKY_NOTE_URL = "/StickyNotes/";
    final static String FULL_BIKE_URL = "/FullBike/";
    final static String OPTIONS_URL = "/Options/";
    private static boolean isSetupDone = false;
    private static boolean isFileSaved = false;
    private List<StickyNote> list = new ArrayList<>();

    @BeforeEach
    public void setup() {
        if (!isFileSaved) {
            list = stickyNoteService.retrieveAllNotes();
            isFileSaved = true;
        }
        if (!isSetupDone) {
            Frame frame = new Frame(GRAVEL, true, false, true);
            FullBike bike = new FullBike("bike", frame, MECHANICAL_DISC, SHIMANO, DROPS, 1L, 11L, STI);
            fullBikeService.create(bike);
            Frame frame1 = new Frame(ROAD, false, true, true);
            FullBike bike1 = new FullBike("bike1", frame1, RIM, SHIMANO, DROPS, 2L, 10L, STI);
            fullBikeService.create(bike1);

            Map<String, Boolean> map3 = new HashMap<>();
            map3.put("This is the message for the third before all method", false);
            StickyNote note3 = new StickyNote("Third Before All Method", map3, false);
            stickyNoteService.create(note3);
            stickyNoteService.create("Gardening Work Left", "Dig more soil from Zebo. Flatten front and back lawns. Seed new grass. Fix nasty bit behind shed", true);
            stickyNoteService.create("Nothing Useful", "This is just to make an extra Sticky Note as I thought 4 would look better than 3!", true);
            stickyNoteService.create("Go for a run!", "Seriously get up early and go for a run!!\nYou're just being lazy!", false);
            isSetupDone = true;
        }
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
    }

    @AfterEach
    public void close() {
        this.session = null;
        this.mockMvc = null;
    }

    @AfterAll
    private void clearup() {
        stickyNoteService.writeNotesToFile(list);
    }


    @Test
    public void test_That_Options_is_returned_with_Brands() throws Exception {
        this.mockMvc.perform(get(OPTIONS_URL + "StartNewBike"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_the_front_can_check_back_end_is_on() throws Exception {
        this.mockMvc.perform(get(TEST_API_URL + "IsThisThingOn"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_a_note_can_be_created() throws Exception {
        DTOnote DTOnote = new DTOnote("Controller test", "Test Message", false);
        this.mockMvc.perform(post(STICKY_NOTE_URL + "AddNote").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(DTOnote))).andExpect(status().isCreated());
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
        isSetupDone = false;
    }

    @Test
    public void test_That_all_Notes_can_be_deleted() throws Exception {
        this.mockMvc.perform(delete(STICKY_NOTE_URL + "DeleteAllNotes"))
                .andExpect(status().isOk());
        isSetupDone = false;
    }

    @Test
    public void test_That_a_Note_can_be_edited() throws Exception {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Do it NOW!", false);
        StickyNote note = new StickyNote(2, "Paint! Boo", map, false);
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
    public void test_That_a_single_Bike_can_be_deleted() throws Exception {
        FullBike bike = fullBikeService.getBikeUsingName("bike1");
        this.mockMvc.perform(delete(FULL_BIKE_URL + "DeleteBike").session(session).contentType("application/json").content(objectMapper.writeValueAsString(bike)))
                .andExpect(status().isAccepted());
        isSetupDone = false;
    }

    @Test
    public void test_That_an_empty_bike_can_be_created() throws Exception {
        FullBike bike = new FullBike();
        this.mockMvc.perform(post(FULL_BIKE_URL + "AddFullBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(bike))).andExpect(status().isCreated());
    }

    @Test
    public void test_That_an_in_flight_bike_can_be_updated() throws Exception {
        Frame frame = new Frame(GRAVEL, true, false, true);
        FullBike bike = new FullBike("bike", frame, HYDRAULIC_DISC, SRAM, DROPS, 1L, 11L, STI);
        bike.setBikeName("Bike Update");
        this.mockMvc.perform(post(FULL_BIKE_URL + "UpdateBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(bike))).andExpect(status().isAccepted());
    }

    @Test
    public void test_That_a_fully_defined_bike_can_be_created() throws Exception {
        Frame frame = new Frame(GRAVEL, false, true, true);
        FrontGears frontGears = new FrontGears(1);
        RearGears rearGears = new RearGears(11);
        FullBike testBike = new FullBike("testBike", frame, MECHANICAL_DISC, SHIMANO, DROPS, 1L, 11L, STI);
        this.mockMvc.perform(post(FULL_BIKE_URL + "AddFullBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(testBike))).andExpect(status().isCreated());
    }
}