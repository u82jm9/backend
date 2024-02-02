package com.homeapp.NonsenseBE.models.bike;

/**
 * The Combined Data object. Primary usage is as a data transfer object for controller API.
 * Allows for Options to be set and controlled in the BE; based on an in-flight design bike.
 */
public class CombinedData {

    private FullBike bike;

    private Options options;

    /**
     * Instantiates a new Combined data.
     */
    public CombinedData() {
    }

    /**
     * Gets bike.
     *
     * @return the bike
     */
    public FullBike getBike() {
        return this.bike;
    }

    /**
     * Sets bike.
     *
     * @param bike the bike
     */
    public void setBike(FullBike bike) {
        this.bike = bike;
    }

    /**
     * Gets options.
     *
     * @return the options
     */
    public Options getOptions() {
        return this.options;
    }

    /**
     * Sets options.
     *
     * @param options the options
     */
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