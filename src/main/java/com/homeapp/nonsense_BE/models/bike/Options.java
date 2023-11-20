package com.homeapp.nonsense_BE.models.bike;

import java.util.ArrayList;
import java.util.List;

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

    public Options() {
    }

    public boolean isShowGroupSetBrand() {
        return showGroupSetBrand;
    }

    public void setShowGroupSetBrand(boolean showGroupSetBrand) {
        this.showGroupSetBrand = showGroupSetBrand;
    }

    public boolean isShowFrontGears() {
        return showFrontGears;
    }

    public void setShowFrontGears(boolean showFrontGears) {
        this.showFrontGears = showFrontGears;
    }

    public boolean isShowRearGears() {
        return showRearGears;
    }

    public void setShowRearGears(boolean showRearGears) {
        this.showRearGears = showRearGears;
    }

    public boolean isShowFrameSizes() {
        return showFrameSizes;
    }

    public void setShowFrameSizes(boolean showFrameSizes) {
        this.showFrameSizes = showFrameSizes;
    }

    public boolean isShowFrameStyles() {
        return showFrameStyles;
    }

    public void setShowFrameStyles(boolean showFrameStyles) {
        this.showFrameStyles = showFrameStyles;
    }

    public boolean isShowBarStyles() {
        return showBarStyles;
    }

    public void setShowBarStyles(boolean showBarStyles) {
        this.showBarStyles = showBarStyles;
    }

    public boolean isShowBrakeStyles() {
        return showBrakeStyles;
    }

    public void setShowBrakeStyles(boolean showBrakeStyles) {
        this.showBrakeStyles = showBrakeStyles;
    }

    public List<Long> getFrameSizes() {
        return frameSizes;
    }

    public void setFrameSizes(List<Long> frameSizes) {
        this.frameSizes = frameSizes;
    }

    public List<String> getFrameStyles() {
        return frameStyles;
    }

    public void setFrameStyles(List<String> frameStyles) {
        this.frameStyles = frameStyles;
    }

    public List<String> getBarStyles() {
        return barStyles;
    }

    public void setBarStyles(List<String> barStyles) {
        this.barStyles = barStyles;
    }

    public List<String> getBrakeStyles() {
        return brakeStyles;
    }

    public void setBrakeStyles(List<String> brakeSyles) {
        this.brakeStyles = brakeSyles;
    }

    public List<String> getGroupsetBrand() {
        return groupsetBrand;
    }

    public void setGroupsetBrand(List<String> groupsetBrand) {
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

    @Override
    public String toString() {
        return "Options{" +
                ", showFrontGears=" + showFrontGears +
                "showGroupsetBrand=" + showGroupSetBrand +
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
                ", brakeSyles=" + brakeStyles +
                ", showBrakeStyles=" + showBrakeStyles +
                '}';
    }
}