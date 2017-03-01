package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.DataRowEntityRowKeyGenerator;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers.CalendarParser;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM10Measurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM25Measurement;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class HistoricalDataProvider {
    private final String TABLE_NAME = "pollution";
    private DataRowEntityRowKeyGenerator generator;
    private AzureTableConnector connector;
    private CalendarParser calendarParser;

    public HistoricalDataProvider() {
        generator = new DataRowEntityRowKeyGenerator();
        connector = new AzureTableConnector();
        calendarParser = new CalendarParser();
    }

    /**
     * Gets a historical data points from the collection of historical data on Azure for
     * each of the specified date/times, at each of the specified locations
     *
     * @param calendars The date/times we are requesting data points for
     * @param locations The locations we are requesting data points for
     * @return The data points from the historical data per calendar per location
     */
    public List<DataPoint> getDataPoints(List<Calendar> calendars, List<Location> locations) throws StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        List<DataPoint> resultList = new LinkedList<>();
        if (calendars.size() != locations.size())
            throw new IllegalArgumentException(String.format("Number of dates (%d) doesn't match number of locations (%d)", calendars.size(), locations.size()));
        for (int i = 0; i < calendars.size(); i++) {
            Calendar currentCalendar = calendars.get(i);
            Location currentLocation = locations.get(i);
            String rowKey = generator.generateRowKey(currentCalendar, currentLocation);
            DataRowEntity currentEntity = connector.getEntityFromTableByRowKey(TABLE_NAME, rowKey);
            resultList.add(convertToDataPoint(currentEntity));
        }
        return resultList;
    }


    /**
     * Creates a datapoint from an entity, calendar and location
     *
     * @param entity The entity (Azure table row) we are creating data point for
     * @return The data points from the historical data per calendar per location
     */
    private DataPoint convertToDataPoint(DataRowEntity entity) {
        Calendar calendar;

        try {
            calendar = calendarParser.getCalendarFromSearchTimestamp(entity.getSearchTimestamp());
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse search timestamp in entity");
        }

        Location location = new Location(entity.getLatitude(), entity.getLongitude());

        NOxMeasurement NOx = entity.getNOx().equals("") ? null : new NOxMeasurement(Double.parseDouble(entity.getNOx()));
        PM10Measurement PM10 = entity.getPM10().equals("") ? null : new PM10Measurement(Double.parseDouble(entity.getPM10()));
        PM25Measurement PM25 = entity.getPM25().equals("") ? null : new PM25Measurement(Double.parseDouble(entity.getPM25()));

        AirDataPoint airDataPoint = new AirDataPoint(NOx, PM10, PM25);
        WeatherDataPoint weatherDataPoint = new WeatherDataPoint();

        return new DataPoint(calendar, location, airDataPoint, weatherDataPoint);
    }


}
