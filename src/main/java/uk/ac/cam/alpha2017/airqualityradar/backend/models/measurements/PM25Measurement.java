package uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements;

/**
 * Created by jirka on 27.2.17.
 */
public class PM25Measurement implements NumberMeasurement {
    private double value;
    private String units;

    public PM25Measurement(double value) {
        this(value, "µg/m³");
    }

    public PM25Measurement(double value, String units) {
        this.value = value;
        this.units = units;
    }

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