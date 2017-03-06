package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.WeatherDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by henry on 06/03/2017.
 */
public class DataPointConverterTest {
    private DataPointConverter converter;
    private AirDataEntity airEntity;
    private WeatherDataEntity weatherEntity;

    @BeforeEach
    public void setup(){
        converter = new DataPointConverter();
        airEntity = new AirDataEntity();
        weatherEntity = new WeatherDataEntity();
        airEntity.setSearchTimestamp("2017-04-09T06:04:08Z");
        weatherEntity.setSearchTimestamp("2017-04-09T06:04:08Z");
        airEntity.setLatitude(5L);
        airEntity.setLongitude(2L);
        weatherEntity.setLongitude(5L);
        weatherEntity.setLongitude(2L);

        airEntity.setNOx("20");
        airEntity.setPM10("30");
        airEntity.setPM25("40");

        weatherEntity.setTemperature("5");
        weatherEntity.setHumidity("90");
        weatherEntity.setWindDirection("NE");
        weatherEntity.setWindSpeed("8");
        weatherEntity.setPressure("6");
        weatherEntity.setRainfallInPastHour("1");

    }

    @Test
    public void testAirToData(){
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity,weatherEntity);
        AirDataPoint airPoint = dataPoint.getAir();

        assertEquals(airPoint.getNox().getValue(),20);
        assertEquals(airPoint.getPm10().getValue(),30);
        assertEquals(airPoint.getPm25().getValue(),40);

    }

    @Test
    public void testAirEmptyValue(){
        airEntity.setNOx("");
        airEntity.setPM10("");
        airEntity.setPM25("");
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity,weatherEntity);
        AirDataPoint airPoint = dataPoint.getAir();

        assertEquals(airPoint.getNox(),null);
        assertEquals(airPoint.getPm10(),null);
        assertEquals(airPoint.getPm25(),null);

    }

    @Test
    public void testWeatherToData(){
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity,weatherEntity);
        WeatherDataPoint weatherPoint =  dataPoint.getWeather();

        assertEquals(weatherPoint.getTemperature().getValue(),5);
        assertEquals(weatherPoint.getHumidity().getValue(),90);
        assertEquals(weatherPoint.getWindDirection().getValue(),"NE");
        assertEquals(weatherPoint.getWindSpeed().getValue(),8);
        assertEquals(weatherPoint.getPressure().getValue(),6);
        assertEquals(weatherPoint.getRainfall().getValue(),1);

    }

    @Test
    public void testWeatherEmptyValue(){
        weatherEntity.setTemperature("");
        weatherEntity.setHumidity("");
        weatherEntity.setWindDirection("");
        weatherEntity.setWindSpeed("");
        weatherEntity.setPressure("");
        weatherEntity.setRainfallInPastHour("");
        DataPoint dataPoint = converter.convertAirAndWeatherToDataPoint(airEntity,weatherEntity);
        WeatherDataPoint weatherPoint =  dataPoint.getWeather();

        assertEquals(weatherPoint.getTemperature(),null);
        assertEquals(weatherPoint.getHumidity(),null);
        assertEquals(weatherPoint.getWindDirection(),null);
        assertEquals(weatherPoint.getWindSpeed(),null);
        assertEquals(weatherPoint.getPressure(),null);
        assertEquals(weatherPoint.getRainfall(),null);

    }
}
