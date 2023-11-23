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
import static com.homeapp.nonsense_BE.models.bike.Enums.GroupsetBrand.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.HandleBarType.*;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.NONE;
import static com.homeapp.nonsense_BE.models.bike.Enums.ShifterStyle.STI;

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
        fullBikeService.create(new FullBike("Gravel", frame1, HYDRAULIC_DISC, SRAM, FLARE, 1L, 11L, STI));

        Frame frame2 = new Frame(ROAD, false, false, true);
        fullBikeService.create(new FullBike("Workhorse", frame2, RIM, SHIMANO, DROPS, 3L, 9L, STI));

        Frame frame3 = new Frame(SINGLE_SPEED, false, false, false);
        fullBikeService.create(new FullBike("Fixie", frame3, RIM, OTHER, BULLHORNS, 1L, 1L, NONE));

        Frame frame4 = new Frame(SINGLE_SPEED, true, false, false);
        fullBikeService.create(new FullBike("City Cruiser", frame4, MECHANICAL_DISC, OTHER, FLAT, 1L, 1L, NONE));
    }
}