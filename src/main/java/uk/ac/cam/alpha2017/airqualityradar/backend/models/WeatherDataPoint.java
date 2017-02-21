package uk.ac.cam.alpha2017.airqualityradar.backend.models;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.HumidityMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PressureMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.RainfallMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.TemperatureMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.WindDirectionMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.WindSpeedMeasurement;

public class WeatherDataPoint {
    private TemperatureMeasurement temperature;
    private HumidityMeasurement humidity;
    private WindDirectionMeasurement windDirection;
    private WindSpeedMeasurement windSpeed;
    private PressureMeasurement pressure;
    private RainfallMeasurement rainfall;
}
