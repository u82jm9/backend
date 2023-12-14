package com.homeapp.nonsense_BE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.models.bike.Frame;
import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import com.homeapp.nonsense_BE.services.FullBikeService;
import com.homeapp.nonsense_BE.services.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

import static com.homeapp.nonsense_BE.models.bike.Enums.BrakeType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.FrameStyle.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.OTHER;
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.SHIMANO;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.*;

@SpringBootApplication
public class HomeAppAttemptOneApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HomeAppAttemptOneApplication.class, args);
    }

    @Autowired
    private StickyNoteService stickyNoteService;
    @Autowired
    private FullBikeService fullBikeService;

    @Override
    public void run(String... args) throws Exception {
        File notesFile = new File("src/main/resources/notes.json");
        ObjectMapper om = new ObjectMapper();
        List<StickyNote> notesFromFile = om
                .readValue(notesFile, new TypeReference<>() {
                });
        for (StickyNote sn : notesFromFile) {
            stickyNoteService.create(sn);
        }

        Frame frame1 = new Frame(GRAVEL, true, false, true);
        FullBike bike1 = new FullBike("Gravel", frame1, HYDRAULIC_DISC, SHIMANO, FLARE, 1L, 11L, STI);
        bike1.setWheelPreference("Expensive");
        fullBikeService.create(bike1);

        Frame frame2 = new Frame(ROAD, false, false, true);
        FullBike bike2 = new FullBike("Workhorse", frame2, RIM, SHIMANO, DROPS, 3L, 9L, STI);
        bike2.setWheelPreference("Cheap");
        fullBikeService.create(bike2);

        Frame frame3 = new Frame(SINGLE_SPEED, false, false, false);
        FullBike bike3 = new FullBike("Fixie", frame3, RIM, OTHER, BULLHORNS, 1L, 1L, NONE);
        bike3.setWheelPreference("Expensive");
        fullBikeService.create(bike3);

        Frame frame4 = new Frame(SINGLE_SPEED, true, false, false);
        FullBike bike4 = new FullBike("City Cruiser", frame4, MECHANICAL_DISC, OTHER, FLAT, 1L, 1L, NONE);
        bike4.setWheelPreference("Cheap");
        fullBikeService.create(bike4);

        Frame frame5 = new Frame(TOUR, true, true, true);
        FullBike bike5 = new FullBike("Tourer", frame5, HYDRAULIC_DISC, SHIMANO, FLAT, 3L, 10L, TRIGGER);
        bike5.setWheelPreference("Expensive");
        fullBikeService.create(bike5);
    }
}