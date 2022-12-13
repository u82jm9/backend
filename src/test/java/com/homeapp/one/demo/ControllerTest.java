package com.homeapp.one.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.one.demo.controller.StickyNoteController;
import com.homeapp.one.demo.models.*;
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

import static com.homeapp.one.demo.models.Enums.BrakeType.*;
import static com.homeapp.one.demo.models.Enums.FrameStyle.*;
import static com.homeapp.one.demo.models.Enums.GroupsetBrand.*;
import static com.homeapp.one.demo.models.Enums.HandleBarType.*;
import static com.homeapp.one.demo.models.Enums.ShifterStyle.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void test_That_the_front_can_check_back_end_is_on() throws Exception {
        this.mockMvc.perform(get(TEST_API_URL + "IsThisThingOn"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_That_a_note_can_be_created() throws Exception {
        StickyNote note = new StickyNote("Controller test", "Noty note");
        this.mockMvc.perform(post(STICKY_NOTE_URL + "AddNote").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(note))).andExpect(status().isCreated());
    }

    @Test
    public void test_That_a_list_of_Notes_can_be_returned() throws Exception {
        this.mockMvc.perform(get(STICKY_NOTE_URL + "GetAll"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test_That_a_list_of_Full_Bikes_can_be_returned() throws Exception {
        this.mockMvc.perform(get(FULL_BIKE_URL + "GetAll"))
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
    public void test_That_a_fully_defined_bike_can_be_created() throws Exception {
        Frame frame = new Frame(GRAVEL, false, true, true, STI);
        FrontGears frontGears = new FrontGears(1, SHIMANO);
        RearGears rearGears = new RearGears(11, SHIMANO);
        FullBike testBike = new FullBike("testBike",frame, MECHANICAL_DISC, DROPS, frontGears, rearGears);
        this.mockMvc.perform(post(FULL_BIKE_URL + "AddFullBike").session(session).contentType("application/json")
                .content(objectMapper.writeValueAsString(testBike))).andExpect(status().isCreated());
    }
}