package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class HistoricalDataProvider {
    private static final String TABLE_NAME = "pollution";
    private final AzureTableConnector connector;

    public HistoricalDataProvider(AzureTableConnector connector) {
        this.connector = connector;
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
        Iterator<DataRowEntity> resultIterator = connector.getEntitiesBetweenCalendars(TABLE_NAME, fromCalendar, toCalendar);
        List<DataPoint> dataPoints = new ArrayList<>();

        resultIterator.forEachRemaining(entity -> dataPoints.add(entity.convertToDataPoint()));

        return dataPoints;
    }
}
