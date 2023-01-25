package com.homeapp.one.demo;

import com.homeapp.one.demo.models.*;
import com.homeapp.one.demo.services.FullBikeService;
import com.homeapp.one.demo.services.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.homeapp.one.demo.models.Enums.BrakeType.*;
import static com.homeapp.one.demo.models.Enums.FrameStyle.*;
import static com.homeapp.one.demo.models.Enums.GroupsetBrand.*;
import static com.homeapp.one.demo.models.Enums.HandleBarType.*;
import static com.homeapp.one.demo.models.Enums.ShifterStyle.*;
import static com.homeapp.one.demo.models.Enums.SramGroupSet.*;
import static com.homeapp.one.demo.models.Enums.ShimanoGroupSet.*;

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
		stickyNoteService.create(new StickyNote("Morning Routine", "Get up with Tristan. Make Coffee. Wake up Lily and Izzy. Make Breakfast. Brush Teeth. Go to work"));
		stickyNoteService.create(new StickyNote("Still to do...", "Create weather scrape, using jSoup. Create photo scrape. Create bike design and price."));
		stickyNoteService.create(new StickyNote("Doctor Call", "Monday 22nd at around 8-30am"));
		stickyNoteService.create(new StickyNote("Gardening Work Left", "Dig more soil from Zebo. Flatten front and back lawns. Seed new grass. Fix nasty bit behind shed"));
		stickyNoteService.create(new StickyNote("Nothing Useful", "This is just to make an extra Sticky Note as I thought 4 would look better than 3!"));
		stickyNoteService.create(new StickyNote("Go for a run!", "Seriously get up early and go for a run!!\nYou're just being lazy!"));

		Frame frame1 = new Frame(GRAVEL, true, false, true, STI);
		FrontGears frontGears1 = new FrontGears(1);
		RearGears rearGears1 = new RearGears(11);
		rearGears1.setSramGroupSet(RIVAL);
		fullBikeService.create(new FullBike("Gravel", frame1, HYDRAULIC_DISC, SRAM, FLARE, frontGears1, rearGears1));

		Frame frame2 = new Frame(ROAD, false, false, true, STI);
		FrontGears frontGears2 = new FrontGears(3);
		RearGears rearGears2 = new RearGears(9);
		rearGears2.setShimanoGroupSet(SORA);
		fullBikeService.create(new FullBike("Workhorse", frame2, RIM, SHIMANO, DROPS, frontGears2, rearGears2));

		Frame frame3 = new Frame(SINGLE_SPEED, false, false, false, NONE);
		FrontGears frontGears3 = new FrontGears(1);
		RearGears rearGears3 = new RearGears(1);
		fullBikeService.create(new FullBike("Fixie", frame3, RIM, OTHER, BULLHORNS, frontGears3, rearGears3));

		Frame frame4 = new Frame(SINGLE_SPEED, true, false, false, NONE);
		FrontGears frontGears4 = new FrontGears(1);
		RearGears rearGears4 = new RearGears(1);
		fullBikeService.create(new FullBike("City Cruiser", frame4, MECHANICAL_DISC, OTHER, FLAT, frontGears4, rearGears4));
	}
}