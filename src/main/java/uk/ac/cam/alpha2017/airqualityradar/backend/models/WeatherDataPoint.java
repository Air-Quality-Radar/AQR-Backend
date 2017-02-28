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

    public WeatherDataPoint(TemperatureMeasurement temperature, HumidityMeasurement humidity,
                            WindDirectionMeasurement windDirection, WindSpeedMeasurement windSpeed,
                            PressureMeasurement pressure, RainfallMeasurement rainfall){
        this.temperature = temperature;
        this.humidity = humidity;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.rainfall = rainfall;
    }

    public TemperatureMeasurement getTemperature() {
        return temperature;
    }

    public HumidityMeasurement getHumidity() {
        return humidity;
    }

    public WindDirectionMeasurement getWindDirection() {
        return windDirection;
    }

    public WindSpeedMeasurement getWindSpeed() {
        return windSpeed;
    }

    public PressureMeasurement getPressure() {
        return pressure;
    }

    public RainfallMeasurement getRainfall() {
        return rainfall;
    }
}
