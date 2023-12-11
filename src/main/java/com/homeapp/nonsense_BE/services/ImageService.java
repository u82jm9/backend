package com.homeapp.nonsense_BE.services;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import com.homeapp.nonsense_BE.models.bike.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private static final Logger LOGGER = LogManager.getLogger(ImageService.class);

    public List<Image> getImages(FullBike b) {
        List<Image> imageList = new ArrayList<>();
        LOGGER.info("Getting Images for Bike: {}", b);
        imageList.add(chooseFrameImage(b));
        imageList.add(chooseBarImage(b));
        imageList.add(chooseBrakeImage(b));
        return imageList;
    }

    private Image chooseFrameImage(FullBike b) {
        Image i = new Image(0, "Frame", "no_image.png", "No Frame Selected");
        switch (b.getFrame().getFrameStyle()) {
            case TOUR: {
                i.setSrc("tour_xxx.png");
                i.setAltText("Tour Frame xxx");
            }
            case SINGLE_SPEED: {
                i.setSrc("fixie_frame.png");
                i.setAltText("Single Speed Frame");
            }
            case ROAD: {
                i.setSrc("road_xxx.png");
                i.setAltText("Road Frame xxx");
            }
            case GRAVEL: {
                i.setSrc("gravel_disc.png");
                i.setAltText("Gravel Frame xxx");
            }
            default: {
                i.setSrc("no_image.png");
                i.setAltText("No Frame Selected");
            }
        }
        if (b.getBrakeType().getName().equals("Rim")) {
            i.setSrc(i.getSrc().replace("xxx", "rim"));
            i.setAltText(i.getAltText().replace("xxx", "Rim"));
        } else {
            i.setSrc(i.getSrc().replace("xxx", "disc"));
            i.setAltText(i.getAltText().replace("xxx", "Disc"));
        }
        return i;
    }

    private Image chooseBarImage(FullBike b) {
        Image i = new Image(1, "Bars", "no_image.png", "No Bars Selected");
        switch (b.getHandleBarType()) {
            case FLARE: {
                i.setSrc("flared_bars.png");
                i.setAltText("Flared Bars");
            }
            case DROPS: {
                i.setSrc("drop_bars.png");
                i.setAltText("Drop Bars");
            }
            case BULLHORNS: {
                i.setSrc("bullhorns_bars.png");
                i.setAltText("Bull Horn Bars");
            }
            case FLAT: {
                i.setSrc("flat_bars.png");
                i.setAltText("Flat Bars");
            }
            default:
                i.setSrc("no_image.png");
                i.setAltText("No Bars Selected");
        }
        return i;
    }

    private Image chooseBrakeImage(FullBike b) {
        Image i = new Image(2, "Brakes", "shimano_xxx.png", "Shimano xxx Brakes");
        if (b.getBrakeType().getName().equals("NOT_REQUIRED")) {
            i.setSrc("no_image.png");
            i.setAltText("No Brakes Required! Brave :) ");
        } else if (b.getBrakeType().getName().equals("Rim")) {
            i.setSrc(i.getSrc().replace("xxx", "rim"));
            i.setAltText(i.getAltText().replace("xxx", "Rim"));
        } else {
            i.setSrc(i.getSrc().replace("xxx", "disc"));
            i.setAltText(i.getAltText().replace("xxx", "Disc"));
        }
        return i;
    }
}