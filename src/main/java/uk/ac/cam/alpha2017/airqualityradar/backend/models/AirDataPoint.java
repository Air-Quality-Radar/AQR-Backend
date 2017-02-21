package uk.ac.cam.alpha2017.airqualityradar.backend.models;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM10Measurement;

public class AirDataPoint {
    private NOxMeasurement nox;
    private PM10Measurement pm10;

    public AirDataPoint(NOxMeasurement nox, PM10Measurement pm10) {
        this.nox = nox;
        this.pm10 = pm10;
    }

    public NOxMeasurement getNox() {
        return nox;
    }

    public PM10Measurement getPm10() {
        return pm10;
    }
}
