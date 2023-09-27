package com.homeapp.one.demo.models.bike;

import com.homeapp.one.demo.models.bike.Enums.*;

import java.util.ArrayList;
import java.util.List;

import static com.homeapp.one.demo.models.bike.Enums.FrameStyle.*;
import static com.homeapp.one.demo.models.bike.Enums.GroupsetBrand.*;

public class Options {

    private List<GroupsetBrand> groupsetBrand = new ArrayList<>();

    private List<Long> numberOfFrontGears;

    private List<Long> numberOfRearGears;

    private List<ShimanoGroupSet> shimanoGroupSet;

    private List<SramGroupSet> sramGroupSet;

    private List<CampagnoloGroupSet> campagnoloGroupSet;

    private List<Long> frameSizes;

    private List<Long> tireClearances;

    private List<FrameStyle> frameStyles;

    private List<HandleBarType> barStyles;

    private List<BrakeType> brakeSyles;

    public Options() {
        List<GroupsetBrand> gs = new ArrayList<>();
        gs.add(SHIMANO);
        gs.add(SRAM);
        gs.add(CAMPAGNOLO);
        setGroupsetBrand(gs);
        List<Long> fSizes = new ArrayList<>();
        fSizes.add(48L);
        fSizes.add(50L);
        fSizes.add(52L);
        fSizes.add(54L);
        fSizes.add(56L);
        setFrameSizes(fSizes);
        List<Long> ts = new ArrayList<>();
        ts.add(23L);
        ts.add(28L);
        ts.add(33L);
        ts.add(38L);
        ts.add(48L);
        setTireClearances(ts);
        List<FrameStyle> fStyle = new ArrayList<>();
        fStyle.add(SINGLE_SPEED);
        fStyle.add(GRAVEL);
        fStyle.add(TOUR);
        fStyle.add(ROAD);
        setFrameStyles(fStyle);
    }

    public List<Long> getFrameSizes() {
        return frameSizes;
    }

    public void setFrameSizes(List<Long> frameSizes) {
        this.frameSizes = frameSizes;
    }

    public List<Long> getTireClearances() {
        return tireClearances;
    }

    public void setTireClearances(List<Long> tireClearances) {
        this.tireClearances = tireClearances;
    }

    public List<FrameStyle> getFrameStyles() {
        return frameStyles;
    }

    public void setFrameStyles(List<FrameStyle> frameStyles) {
        this.frameStyles = frameStyles;
    }

    public List<HandleBarType> getBarStyles() {
        return barStyles;
    }

    public void setBarStyles(List<HandleBarType> barStyles) {
        this.barStyles = barStyles;
    }

    public List<BrakeType> getBrakeSyles() {
        return brakeSyles;
    }

    public void setBrakeSyles(List<BrakeType> brakeSyles) {
        this.brakeSyles = brakeSyles;
    }

    public List<GroupsetBrand> getGroupsetBrand() {
        return groupsetBrand;
    }

    public void setGroupsetBrand(List<GroupsetBrand> groupsetBrand) {
        this.groupsetBrand = groupsetBrand;
    }

    public List<Long> getNumberOfFrontGears() {
        return numberOfFrontGears;
    }

    public void setNumberOfFrontGears(List<Long> numberOfFrontGears) {
        this.numberOfFrontGears = numberOfFrontGears;
    }

    public List<Long> getNumberOfRearGears() {
        return numberOfRearGears;
    }

    public void setNumberOfRearGears(List<Long> numberOfRearGears) {
        this.numberOfRearGears = numberOfRearGears;
    }

    public List<ShimanoGroupSet> getShimanoGroupSet() {
        return shimanoGroupSet;
    }

    public void setShimanoGroupSet(List<ShimanoGroupSet> shimanoGroupSet) {
        this.shimanoGroupSet = shimanoGroupSet;
    }

    public List<SramGroupSet> getSramGroupSet() {
        return sramGroupSet;
    }

    public void setSramGroupSet(List<SramGroupSet> sramGroupSet) {
        this.sramGroupSet = sramGroupSet;
    }

    public List<CampagnoloGroupSet> getCampagnoloGroupSet() {
        return campagnoloGroupSet;
    }

    public void setCampagnoloGroupSet(List<CampagnoloGroupSet> campagnoloGroupSet) {
        this.campagnoloGroupSet = campagnoloGroupSet;
    }

    @Override
    public String toString() {
        return "Options{" +
                "groupsetBrand=" + groupsetBrand +
                ", numberOfFrontGears=" + numberOfFrontGears +
                ", numberOfRearGears=" + numberOfRearGears +
                ", shimanoGroupSet=" + shimanoGroupSet +
                ", sramGroupSet=" + sramGroupSet +
                ", campagnoloGroupSet=" + campagnoloGroupSet +
                ", frameSizes=" + frameSizes +
                ", tireClearances=" + tireClearances +
                ", frameStyles=" + frameStyles +
                ", barStyles=" + barStyles +
                ", brakeSyles=" + brakeSyles +
                '}';
    }
}