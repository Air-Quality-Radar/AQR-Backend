package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.DataRowEntitityRowKeyGenerator.generateRowKey;

public class HistoricalDataProvider {
    // Define constants for filters.
    final String TABLE_NAME = "rawpollution";

    /**
     * Gets a historical data points from the collection of historical data on Azure for
     * each of the specified date/times, at each of the specified locations
     *
     * @param calendars The date/times we are requesting data points for
     * @param locations The locations we are requesting data points for
     * @return The data points from the historical data per calendar per location
     */
    public List<DataPoint> getDataPoints(List<Calendar> calendars, List<Location> locations) throws StorageException, InvalidKeyException, URISyntaxException {
        List<DataPoint> resultList = new LinkedList<>();
        if (calendars.size() != locations.size())
            throw new IllegalArgumentException(String.format("Number of dates (%d) doesn't match number of locations (%d)", calendars.size(), locations.size()));
        for (int i=0; i<calendars.size(); i++) {
            Calendar currentCalendar = calendars.get(i);
            Location currentLocation = locations.get(i);
            String rowKey = generateRowKey(currentCalendar, currentLocation);
            DataRowEntity currentEntity = AzureTableConnector.getEntityFromTableByRowKey(TABLE_NAME, rowKey);
            resultList.add(createDataPoint(currentCalendar, currentLocation, currentEntity));
        }
        return resultList;
    }

    private DataPoint createDataPoint(Calendar calendar, Location location, DataRowEntity entity) {
        //TODO: Create AirDataPOint and WeatherDataPoint in order to create DataPoint. Then return that.
        AirDataPoint airDataPoint = new AirDataPoint(new NOxMeasurement(entity.getNOx()), );
        WeatherDataPoint weatherDataPoint = new WeatherDataPoint();
        new DataPoint(calendar, location, airDataPoint, weatherDataPoint);
    }


}
