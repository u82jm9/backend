package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.Enums.FrameStyle;
import com.homeapp.one.demo.models.FullBike;
import com.homeapp.one.demo.repository.FullBikeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FullBikeService {

    private static Logger LOGGER = LogManager.getLogger(FullBikeService.class);

    @Autowired
    private FullBikeDao fullBikeDao;

    public List<FullBike> getAllFullBikes() {
        List<FullBike> bikeList = fullBikeDao.findAll();
        LOGGER.info("Getting list of all bikes, number returned: " + bikeList.size());
        return bikeList;
    }

    public void create(FullBike bike) {
        LOGGER.info(("Adding new bike to DB!"));
        fullBikeDao.save(bike);
    }

    public List<FrameStyle> getAllFrameStyles() {
        List<FrameStyle> frameStyles = Arrays.stream(FrameStyle.values()).collect(Collectors.toList());
        LOGGER.info("Getting all frames available, number returned: " + frameStyles.size());
        return frameStyles;
    }
}