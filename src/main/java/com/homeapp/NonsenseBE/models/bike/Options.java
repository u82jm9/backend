package com.homeapp.NonsenseBE.models.bike;

import java.util.ArrayList;
import java.util.List;

/**
 * The Options object. This is used to control the options available to the user on the FE.
 * This strategy hopefully means that an INVALID bike is never designed.
 */
public class Options {

    private List<String> groupsetBrand = new ArrayList<>();

    private boolean showGroupSetBrand = false;

    private List<Long> numberOfFrontGears = new ArrayList<>();

    private boolean showFrontGears = false;

    private List<Long> numberOfRearGears = new ArrayList<>();

    private boolean showRearGears = false;

    private List<Long> frameSizes = new ArrayList<>();

    private boolean showFrameSizes = false;

    private List<String> frameStyles = new ArrayList<>();

    private boolean showFrameStyles = false;

    private List<String> barStyles = new ArrayList<>();

    private boolean showBarStyles = false;

    private List<String> brakeStyles = new ArrayList<>();

    private boolean showBrakeStyles = false;

    private List<String> wheelPreference = new ArrayList<>();

    private boolean showWheelPreference = false;

    /**
     * Instantiates a new Options.
     */
    public Options() {
    }

    /**
     * Is show group set brand boolean.
     *
     * @return the boolean
     */
    public boolean isShowGroupSetBrand() {
        return showGroupSetBrand;
    }

    /**
     * Sets show group set brand.
     *
     * @param showGroupSetBrand the show group set brand
     */
    public void setShowGroupSetBrand(boolean showGroupSetBrand) {
        this.showGroupSetBrand = showGroupSetBrand;
    }

    /**
     * Is show front gears boolean.
     *
     * @return the boolean
     */
    public boolean isShowFrontGears() {
        return showFrontGears;
    }

    /**
     * Sets show front gears.
     *
     * @param showFrontGears the show front gears
     */
    public void setShowFrontGears(boolean showFrontGears) {
        this.showFrontGears = showFrontGears;
    }

    /**
     * Is show rear gears boolean.
     *
     * @return the boolean
     */
    public boolean isShowRearGears() {
        return showRearGears;
    }

    /**
     * Sets show rear gears.
     *
     * @param showRearGears the show rear gears
     */
    public void setShowRearGears(boolean showRearGears) {
        this.showRearGears = showRearGears;
    }

    /**
     * Is show frame sizes boolean.
     *
     * @return the boolean
     */
    public boolean isShowFrameSizes() {
        return showFrameSizes;
    }

    /**
     * Sets show frame sizes.
     *
     * @param showFrameSizes the show frame sizes
     */
    public void setShowFrameSizes(boolean showFrameSizes) {
        this.showFrameSizes = showFrameSizes;
    }

    /**
     * Is show frame styles boolean.
     *
     * @return the boolean
     */
    public boolean isShowFrameStyles() {
        return showFrameStyles;
    }

    /**
     * Sets show frame styles.
     *
     * @param showFrameStyles the show frame styles
     */
    public void setShowFrameStyles(boolean showFrameStyles) {
        this.showFrameStyles = showFrameStyles;
    }

    /**
     * Is show bar styles boolean.
     *
     * @return the boolean
     */
    public boolean isShowBarStyles() {
        return showBarStyles;
    }

    /**
     * Sets show bar styles.
     *
     * @param showBarStyles the show bar styles
     */
    public void setShowBarStyles(boolean showBarStyles) {
        this.showBarStyles = showBarStyles;
    }

    /**
     * Is show brake styles boolean.
     *
     * @return the boolean
     */
    public boolean isShowBrakeStyles() {
        return showBrakeStyles;
    }

    /**
     * Sets show brake styles.
     *
     * @param showBrakeStyles the show brake styles
     */
    public void setShowBrakeStyles(boolean showBrakeStyles) {
        this.showBrakeStyles = showBrakeStyles;
    }

    /**
     * Gets wheel preference.
     *
     * @return the wheel preference
     */
    public List<String> getWheelPreference() {
        return wheelPreference;
    }

    /**
     * Sets wheel preference.
     *
     * @param wheelPreference the wheel preference
     */
    public void setWheelPreference(List<String> wheelPreference) {
        this.wheelPreference = wheelPreference;
    }

    /**
     * Is show wheel preference boolean.
     *
     * @return the boolean
     */
    public boolean isShowWheelPreference() {
        return showWheelPreference;
    }

    /**
     * Sets show wheel preference.
     *
     * @param showWheelPreference the show wheel preference
     */
    public void setShowWheelPreference(boolean showWheelPreference) {
        this.showWheelPreference = showWheelPreference;
    }

    /**
     * Gets frame sizes.
     *
     * @return the frame sizes
     */
    public List<Long> getFrameSizes() {
        return frameSizes;
    }

    /**
     * Sets frame sizes.
     *
     * @param frameSizes the frame sizes
     */
    public void setFrameSizes(List<Long> frameSizes) {
        this.frameSizes = frameSizes;
    }

    /**
     * Gets frame styles.
     *
     * @return the frame styles
     */
    public List<String> getFrameStyles() {
        return frameStyles;
    }

    /**
     * Sets frame styles.
     *
     * @param frameStyles the frame styles
     */
    public void setFrameStyles(List<String> frameStyles) {
        this.frameStyles = frameStyles;
    }

    /**
     * Gets bar styles.
     *
     * @return the bar styles
     */
    public List<String> getBarStyles() {
        return barStyles;
    }

    /**
     * Sets bar styles.
     *
     * @param barStyles the bar styles
     */
    public void setBarStyles(List<String> barStyles) {
        this.barStyles = barStyles;
    }

    /**
     * Gets brake styles.
     *
     * @return the brake styles
     */
    public List<String> getBrakeStyles() {
        return brakeStyles;
    }

    /**
     * Sets brake styles.
     *
     * @param brakeSyles the brake syles
     */
    public void setBrakeStyles(List<String> brakeSyles) {
        this.brakeStyles = brakeSyles;
    }

    /**
     * Gets groupset brand.
     *
     * @return the groupset brand
     */
    public List<String> getGroupsetBrand() {
        return groupsetBrand;
    }

    /**
     * Sets groupset brand.
     *
     * @param groupsetBrand the groupset brand
     */
    public void setGroupsetBrand(List<String> groupsetBrand) {
        this.groupsetBrand = groupsetBrand;
    }

    /**
     * Gets number of front gears.
     *
     * @return the number of front gears
     */
    public List<Long> getNumberOfFrontGears() {
        return numberOfFrontGears;
    }

    /**
     * Sets number of front gears.
     *
     * @param numberOfFrontGears the number of front gears
     */
    public void setNumberOfFrontGears(List<Long> numberOfFrontGears) {
        this.numberOfFrontGears = numberOfFrontGears;
    }

    /**
     * Gets number of rear gears.
     *
     * @return the number of rear gears
     */
    public List<Long> getNumberOfRearGears() {
        return numberOfRearGears;
    }

    /**
     * Sets number of rear gears.
     *
     * @param numberOfRearGears the number of rear gears
     */
    public void setNumberOfRearGears(List<Long> numberOfRearGears) {
        this.numberOfRearGears = numberOfRearGears;
    }

    @Override
    public String toString() {
        return "Options{" +
                "groupsetBrand=" + groupsetBrand +
                ", showGroupSetBrand=" + showGroupSetBrand +
                ", numberOfFrontGears=" + numberOfFrontGears +
                ", showFrontGears=" + showFrontGears +
                ", numberOfRearGears=" + numberOfRearGears +
                ", showRearGears=" + showRearGears +
                ", frameSizes=" + frameSizes +
                ", showFrameSizes=" + showFrameSizes +
                ", frameStyles=" + frameStyles +
                ", showFrameStyles=" + showFrameStyles +
                ", barStyles=" + barStyles +
                ", showBarStyles=" + showBarStyles +
                ", brakeStyles=" + brakeStyles +
                ", showBrakeStyles=" + showBrakeStyles +
                ", wheelPreference=" + wheelPreference +
                ", showWheelPreference=" + showWheelPreference +
                '}';
    }
}