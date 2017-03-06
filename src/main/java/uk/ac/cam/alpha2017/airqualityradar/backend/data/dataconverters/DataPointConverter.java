package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.WeatherDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.*;

import java.text.ParseException;
import java.util.Calendar;

public class DataPointConverter {
    private final static int locationScalingFactor = 1000000; // 1 million

    /**
     * Creates a DataPoint from an air and weather entity
     *
     * @return The data point corresponding to these entities
     */
    public DataPoint convertAirAndWeatherToDataPoint(AirDataEntity airDataEntity, WeatherDataEntity weatherDataEntity, boolean isPredicted) {
        Calendar calendar;

        try {
            calendar = CalendarConverter.getCalendarFromSearchTimestamp(airDataEntity.getSearchTimestamp());
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse search timestamp in entity");
        }

        Location location = null;

        if (airDataEntity.getLatitude() != null && airDataEntity.getLongitude() != null) {
            location = new Location(airDataEntity.getLatitude().doubleValue() / locationScalingFactor, airDataEntity.getLongitude().doubleValue() / locationScalingFactor);
        }

        AirDataPoint airDataPoint = convertAirToDataPoint(airDataEntity);
        WeatherDataPoint weatherDataPoint;

        if (weatherDataEntity == null) {
            weatherDataPoint = null;
        }
        else {
            weatherDataPoint = convertWeatherToDataPoint(weatherDataEntity);
        }

        return new DataPoint(calendar, location, airDataPoint, weatherDataPoint, isPredicted);
    }

    private AirDataPoint convertAirToDataPoint (AirDataEntity airDataEntity) {
        NOxMeasurement NOx = airDataEntity.getNOx() == null ? null : new NOxMeasurement(airDataEntity.getNOx());
        PM10Measurement PM10 = airDataEntity.getPM10() == null ? null : new PM10Measurement(airDataEntity.getPM10());
        PM25Measurement PM25 = airDataEntity.getPM25() == null ? null : new PM25Measurement(airDataEntity.getPM25());

        return new AirDataPoint(NOx, PM10, PM25);
    }

    private WeatherDataPoint convertWeatherToDataPoint(WeatherDataEntity weatherDataEntity) {
        String temperature = weatherDataEntity.getTemperature();
        TemperatureMeasurement temperatureMeasurement = (temperature == null || temperature.equals("")) ? null : new TemperatureMeasurement(Double.parseDouble(temperature));

        String humidity = weatherDataEntity.getHumidity();
        HumidityMeasurement humidityMeasurement = (humidity == null || humidity.equals("")) ? null : new HumidityMeasurement(Double.parseDouble(humidity));

        String windDirection = weatherDataEntity.getWindDirection();
        WindDirectionMeasurement windDirectionMeasurement = (windDirection == null || windDirection.equals("")) ? null : new WindDirectionMeasurement(windDirection);

        String windSpeed = weatherDataEntity.getWindSpeed();
        WindSpeedMeasurement windSpeedMeasurement = (windSpeed == null || windSpeed.equals("")) ? null : new WindSpeedMeasurement(Double.parseDouble(windSpeed));

        String pressure = weatherDataEntity.getPressure();
        PressureMeasurement pressureMeasurement = (pressure == null || pressure.equals("")) ? null : new PressureMeasurement(Double.parseDouble(pressure));

        String rainfall = weatherDataEntity.getRainfallInPastHour();
        RainfallMeasurement rainfallMeasurement = (rainfall == null || rainfall.equals("")) ? null : new RainfallMeasurement(Double.parseDouble(rainfall));

        return new WeatherDataPoint(temperatureMeasurement, humidityMeasurement, windDirectionMeasurement, windSpeedMeasurement, pressureMeasurement, rainfallMeasurement);
    }
}
