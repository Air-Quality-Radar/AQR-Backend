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
import java.util.Collections;
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
     * @param toCalendar   The date and time that we will not return data points after
     * @return The data points from the historical data per calendar per location
     */
    public List<DataPoint> getDataPoints(Calendar fromCalendar, Calendar toCalendar) throws StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        ArrayList<AirDataEntity> airDataResultList = new ArrayList<>();
        ArrayList<WeatherDataEntity> weatherDataResultList = new ArrayList<>();

        //Convert iterators to list using Java8 syntactic sugar & lambdas <3
        connector.getEntitiesBetweenCalendars(HISTORICAL_AIR_DATA_TABLE_NAME, fromCalendar, toCalendar, AirDataEntity.class).forEachRemaining(airDataResultList::add);
        connector.getEntitiesBetweenCalendars(HISTORICAL_WEATHER_DATA_TABLE_NAME, fromCalendar, toCalendar, WeatherDataEntity.class).forEachRemaining(weatherDataResultList::add);

        //Will error if no weatherDataResults retrieved but I think that's appropriate (otherwise mapDataPoints would error)
        if (weatherDataResultList.size() == 0) throw new IllegalStateException("Weather data is empty");
        return mapDataPoints(weatherDataResultList, airDataResultList);
    }

    /**
     * Maps historical air quality data with historical weather data (both retrieved from Azure)
     *
     * @param weatherDataResultList A list of historical weather data retrived from Azure. ArrayList required for fast sorting.
     * @param airDataResultList A list of historical air quality data retrived from Azure. ArrayList required for fast sorting.
     * @return The data points including both weather and air quality from the historical data per calendar per location
     */
    private ArrayList<DataPoint> mapDataPoints(List<WeatherDataEntity> weatherDataResultList, List<AirDataEntity> airDataResultList) {
        //Sort lists
        Collections.sort(airDataResultList);
        Collections.sort(weatherDataResultList);

        //Setup
        int weatherIndex = 0;
        WeatherDataEntity currentWeatherEntity = weatherDataResultList.get(weatherIndex);
        ArrayList<DataPoint> dataPoints = new ArrayList<>(weatherDataResultList.size());

        for (AirDataEntity airDataEntity : airDataResultList) {
            //if weatherDataResultList has next
            if ((weatherIndex + 1) < weatherDataResultList.size()) {
                //If weather entity is newer than air data entity, go through the list to find one that's not
                while (airDataEntity.getSearchTimestamp().compareTo(currentWeatherEntity.getSearchTimestamp()) > 0) {
                    weatherIndex++;
                    //if no more weather results just connect all remaining air data points with null
                    if (weatherIndex >= weatherDataResultList.size()) {
                        currentWeatherEntity = null;
                        break;
                    }
                    //else go on traversing the list until you find an up to date point
                    else currentWeatherEntity = weatherDataResultList.get(weatherIndex);
                }
            }
            dataPoints.add(dataPointConverter.convertAirAndWeatherToDataPoint(airDataEntity, currentWeatherEntity));
        }
        return dataPoints;

    }
}

