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
import java.util.*;

public abstract class AbstractDataProvider {
    private final AzureTableConnector connector;
    private final DataPointConverter dataPointConverter;

    public AbstractDataProvider(AzureTableConnector connector, DataPointConverter dataPointConverter) {
        this.connector = connector;
        this.dataPointConverter = dataPointConverter;
    }

    /**
     * Gets data points from the specified collections of historical and weather data on Azure for
     * each of the specified date/times, at each of the specified locations
     *
     * @param fromCalendar The date and time to start return data points from
     * @param toCalendar   The date and time that we will not return data points after
     * @return The data points from the data per calendar per location
     */
    public List<DataPoint> getDataPoints(Calendar fromCalendar, Calendar toCalendar) throws StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        ArrayList<AirDataEntity> airDataResultList = new ArrayList<>();
        ArrayList<WeatherDataEntity> weatherDataResultList = new ArrayList<>();

        // Convert iterators to list using Java8 syntactic sugar & lambdas <3
        connector.getEntitiesBetweenCalendars(getAirDataTableName(), fromCalendar, toCalendar, AirDataEntity.class).forEachRemaining(airDataResultList::add);
        connector.getEntitiesBetweenCalendars(getWeatherDataTableName(), fromCalendar, toCalendar, WeatherDataEntity.class).forEachRemaining(weatherDataResultList::add);

        return mapDataPoints(weatherDataResultList, airDataResultList);
    }

    /**
     * Maps air quality data and weather data (both retrieved from Azure) to DataPoints
     *
     * @param weatherDataResultList A list of weather data retrieved from Azure. ArrayList required for fast sorting.
     * @param airDataResultList     A list of air quality data retrieved from Azure. ArrayList required for fast sorting.
     * @return The data points including both weather and air quality from the data per calendar per location
     */
    private ArrayList<DataPoint> mapDataPoints(List<WeatherDataEntity> weatherDataResultList, List<AirDataEntity> airDataResultList) {
        // Sort lists & convert to iterables
        Collections.sort(airDataResultList);
        Collections.sort(weatherDataResultList);
        Iterator<AirDataEntity> airDataEntityIterator = airDataResultList.iterator();
        Iterator<WeatherDataEntity> weatherDataEntityIterator = weatherDataResultList.iterator();

        // Setup
        WeatherDataEntity currentWeatherEntity = null;
        if (weatherDataEntityIterator.hasNext()) {
            currentWeatherEntity =  weatherDataEntityIterator.next();
        }
        AirDataEntity currentAirDataEntity;
        ArrayList<DataPoint> dataPoints = new ArrayList<>(airDataResultList.size());

        while (airDataEntityIterator.hasNext()) {
            currentAirDataEntity = airDataEntityIterator.next();
            if (weatherDataEntityIterator.hasNext()) {
                // If weather entity is newer than air data entity, go through the list to find one that's not
                while (currentAirDataEntity.getSearchTimestamp().compareTo(currentWeatherEntity.getSearchTimestamp()) > 0) {
                    // If no more weather results just connect all remaining air data points with null
                    if (!(weatherDataEntityIterator.hasNext())) {
                        currentWeatherEntity = null;
                        break;
                    } else {
                        // else go on traversing the list until you find an up to date point
                        currentWeatherEntity = weatherDataEntityIterator.next();
                    }
                }
            }
            dataPoints.add(dataPointConverter.convertAirAndWeatherToDataPoint(currentAirDataEntity, currentWeatherEntity, isPredicted()));
        }

        return dataPoints;
    }

    abstract String getAirDataTableName();
    abstract String getWeatherDataTableName();
    abstract boolean isPredicted();

}
