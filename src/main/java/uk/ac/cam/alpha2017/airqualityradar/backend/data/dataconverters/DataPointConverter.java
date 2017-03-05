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
    private final static int locationScalingFactor = 1000000; //1 million

    /**
     * Creates a DataPoint from an air and weather entity
     *
     * @return The data point corresponding to these entities
     */
    public DataPoint convertAirAndWeatherToDataPoint(AirDataEntity airDataEntity, WeatherDataEntity weatherDataEntity) {
        Calendar calendar;

        try {
            calendar = CalendarConverter.getCalendarFromSearchTimestamp(airDataEntity.getSearchTimestamp());
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse search timestamp in entity");
        }

        Location location = new Location(airDataEntity.getLatitude().doubleValue() / locationScalingFactor, airDataEntity.getLongitude().doubleValue() / locationScalingFactor);
        AirDataPoint airDataPoint = convertAirToDataPoint(airDataEntity);
        WeatherDataPoint weatherDataPoint;

        if (weatherDataEntity == null) {
            weatherDataPoint = null;
        }
        else {
            weatherDataPoint = convertWeatherToDataPoint(weatherDataEntity);
        }

        return new DataPoint(calendar, location, airDataPoint, weatherDataPoint);
    }

    private AirDataPoint convertAirToDataPoint (AirDataEntity airDataEntity) {
        NOxMeasurement NOx = airDataEntity.getNOx().equals("") ? null : new NOxMeasurement(Double.parseDouble(airDataEntity.getNOx()));
        PM10Measurement PM10 = airDataEntity.getPM10().equals("") ? null : new PM10Measurement(Double.parseDouble(airDataEntity.getPM10()));
        PM25Measurement PM25 = airDataEntity.getPM25().equals("") ? null : new PM25Measurement(Double.parseDouble(airDataEntity.getPM25()));

        return new AirDataPoint(NOx, PM10, PM25);
    }

    private WeatherDataPoint convertWeatherToDataPoint(WeatherDataEntity weatherDataEntity) {
        TemperatureMeasurement temperatureMeasurement = weatherDataEntity.getTemperature().equals("") ? null : new TemperatureMeasurement(Double.parseDouble(weatherDataEntity.getTemperature()));
        HumidityMeasurement humidityMeasurement = weatherDataEntity.getHumidity().equals("") ? null : new HumidityMeasurement(Double.parseDouble(weatherDataEntity.getHumidity()));
        WindDirectionMeasurement windDirectionMeasurement = weatherDataEntity.getWindDirection().equals("") ? null : new WindDirectionMeasurement(weatherDataEntity.getWindDirection());
        WindSpeedMeasurement windSpeedMeasurement = weatherDataEntity.getWindSpeed().equals("") ? null : new WindSpeedMeasurement(Double.parseDouble(weatherDataEntity.getWindSpeed()));
        PressureMeasurement pressureMeasurement = weatherDataEntity.getPressure().equals("") ? null : new PressureMeasurement(Double.parseDouble(weatherDataEntity.getPressure()));
        RainfallMeasurement rainfallMeasurement = weatherDataEntity.getRainfallInPastHour().equals("") ? null : new RainfallMeasurement(Double.parseDouble(weatherDataEntity.getRainfallInPastHour()));

        return new WeatherDataPoint(temperatureMeasurement, humidityMeasurement, windDirectionMeasurement, windSpeedMeasurement, pressureMeasurement, rainfallMeasurement);
    }
}
