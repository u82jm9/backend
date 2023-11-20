package com.homeapp.nonsense_BE.models.bike;

public class CombinedData {

    private FullBike bike;

    private Options options;

    public CombinedData() {
    }

    public FullBike getBike() {
        return this.bike;
    }

    public void setBike(FullBike bike) {
        this.bike = bike;
    }

    public Options getOptions() {
        return this.options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CombinedData{" +
                "bike=" + bike +
                ", options=" + options +
                '}';
    }
}