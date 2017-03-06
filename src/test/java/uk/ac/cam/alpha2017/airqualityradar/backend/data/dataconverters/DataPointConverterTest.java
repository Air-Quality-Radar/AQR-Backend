package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.WeatherDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataPointConverterTest {
    private DataPointConverter converter;
    private AirDataEntity airEntity;
    private WeatherDataEntity weatherEntity;

    @BeforeEach
    public void setup() {
        converter = new DataPointConverter();
        airEntity = new AirDataEntity("", "", "2017-04-09T06:04:08Z", 2017, 129, 364, 5L, 2L, 20.0, 30.0, 40.0);
        weatherEntity = new WeatherDataEntity();
        weatherEntity.setSearchTimestamp("2017-04-09T06:04:08Z");
        weatherEntity.setLongitude(5L);
        weatherEntity.setLongitude(2L);

        weatherEntity.setTemperature("5");
        weatherEntity.setHumidity("90");
        weatherEntity.setWindDirection("NE");
        weatherEntity.setWindSpeed("8");
        weatherEntity.setPressure("6");
        weatherEntity.setRainfallInPastHour("1");

    }

    @Test
    public void testAirToData() {
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity, weatherEntity, false);
        AirDataPoint airPoint = dataPoint.getAir();

        assertEquals(airPoint.getNox().getValue(), 20);
        assertEquals(airPoint.getPm10().getValue(), 30);
        assertEquals(airPoint.getPm25().getValue(), 40);

    }

    @Test
    public void testEmptyAirValuesReturnNull() {
        airEntity = new AirDataEntity("", "", "2017-04-09T06:04:08Z", 2017, 129, 364, 5L, 2L, null, null, null);

        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity, weatherEntity, false);
        AirDataPoint airPoint = dataPoint.getAir();

        assertEquals(airPoint.getNox(), null);
        assertEquals(airPoint.getPm10(), null);
        assertEquals(airPoint.getPm25(), null);

    }

    @Test
    public void testWeatherToData() {
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity, weatherEntity, false);
        WeatherDataPoint weatherPoint =  dataPoint.getWeather();

        assertEquals(weatherPoint.getTemperature().getValue(), 5);
        assertEquals(weatherPoint.getHumidity().getValue(), 90);
        assertEquals(weatherPoint.getWindDirection().getValue(), "NE");
        assertEquals(weatherPoint.getWindSpeed().getValue(), 8);
        assertEquals(weatherPoint.getPressure().getValue(), 6);
        assertEquals(weatherPoint.getRainfall().getValue(), 1);

    }

    @Test
    public void testEmptyWeatherValuesReturnNull() {
        weatherEntity.setTemperature("");
        weatherEntity.setHumidity("");
        weatherEntity.setWindDirection("");
        weatherEntity.setWindSpeed("");
        weatherEntity.setPressure("");
        weatherEntity.setRainfallInPastHour("");
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity, weatherEntity, false);
        WeatherDataPoint weatherPoint =  dataPoint.getWeather();

        assertEquals(weatherPoint.getTemperature(), null);
        assertEquals(weatherPoint.getHumidity(), null);
        assertEquals(weatherPoint.getWindDirection(), null);
        assertEquals(weatherPoint.getWindSpeed(), null);
        assertEquals(weatherPoint.getPressure(), null);
        assertEquals(weatherPoint.getRainfall(), null);

    }
}
