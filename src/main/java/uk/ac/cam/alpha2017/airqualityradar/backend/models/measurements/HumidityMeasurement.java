package uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements;

public class HumidityMeasurement implements NumberMeasurement {
    /**
     * Gives the units used for the measurement
     *
     * @return Units the measurement is measured in
     */
    @Override
    public String getUnits() {
        return null;
    }

    /**
     * Gives the value of the measurement
     *
     * @return Numerical value of the measurement, in units specified by getUnits()
     */
    @Override
    public double getValue() {
        return 0;
    }
}
