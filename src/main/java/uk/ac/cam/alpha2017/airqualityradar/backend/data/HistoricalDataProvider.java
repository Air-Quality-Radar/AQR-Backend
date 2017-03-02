package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.DataPointConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.WeatherDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class HistoricalDataProvider {
    private static final String HISTORICAL_AIR_DATA_TABLE_NAME = "pollution";
    private static final String HISTORICAL_WEATHER_DATA_TABLE_NAME = "weather";
    private final AzureTableConnector connector;
    private final DataPointConverter dataPointConverter;

    public HistoricalDataProvider(AzureTableConnector connector, DataPointConverter dataPointConverter) {
        this.connector = connector;
        this.dataPointConverter = dataPointConverter;
    }

    /**
     * Gets a historical data points from the collection of historical data on Azure for
     * each of the specified date/times, at each of the specified locations
     *
     * @param fromCalendar The date and time to start return data points from
     * @param toCalendar The date and time that we will not return data points after
     * @return The data points from the historical data per calendar per location
     */
    public List<DataPoint> getDataPoints(Calendar fromCalendar, Calendar toCalendar) throws StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        Iterator<AirDataEntity> airDataResultIterator = connector.getEntitiesBetweenCalendars(HISTORICAL_AIR_DATA_TABLE_NAME, fromCalendar, toCalendar, AirDataEntity.class);
        Iterator<WeatherDataEntity> weatherDataResultIterator = connector.getEntitiesBetweenCalendars(HISTORICAL_WEATHER_DATA_TABLE_NAME, fromCalendar, toCalendar, WeatherDataEntity.class);
        List<DataPoint> dataPoints = new ArrayList<>();

        airDataResultIterator.forEachRemaining(entity -> dataPoints.add(dataPointConverter.convertAirAndWeatherToDataPoint(entity, null)));

        return dataPoints;
    }
}
