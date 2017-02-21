package uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements;

public interface NumberMeasurement {
    /**
     * Gives the units used for the measurement
     *
     * @return Units the measurement is measured in
     */
    String getUnits();

    /**
     * Gives the value of the measurement
     *
     * @return Numerical value of the measurement, in units specified by getUnits()
     */
    double getValue();
}
