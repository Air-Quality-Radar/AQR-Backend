package uk.ac.cam.alpha2017.airqualityradar.backend.models;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM10Measurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM25Measurement;

public class AirDataPoint {
    private NOxMeasurement nox;
    private PM10Measurement pm10;
    private PM25Measurement pm25;

    public AirDataPoint(NOxMeasurement nox, PM10Measurement pm10, PM25Measurement pm25) {
        this.nox = nox;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }

    public NOxMeasurement getNox() {
        return nox;
    }

    public PM10Measurement getPm10() {
        return pm10;
    }

    public PM25Measurement getPm25() {
        return pm25;
    }

}
