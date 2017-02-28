package uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements;

public class RainfallMeasurement implements NumberMeasurement {

    private String units;
    private double value;

    public RainfallMeasurement(double value){
        this.value = value;
    }
    /*
    /**
     * Gives the units used for the measurement
     *
     * @return Units the measurement is measured in
     */
    @Override
    public String getUnits() {
        return units;
    }

    /**
     * Gives the value of the measurement
     *
     * @return Numerical value of the measurement, in units specified by getUnits()
     */
    @Override
    public double getValue() {
        return value;
    }
}
