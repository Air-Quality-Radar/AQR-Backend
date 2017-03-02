package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.HistoricalDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.List;

public class RadarDataService implements DataService {

    private final HistoricalDataProvider historicalDataProvider;

    public RadarDataService(HistoricalDataProvider historicalDataProvider) {
        this.historicalDataProvider = historicalDataProvider;
    }

    public List<DataPoint> getDataPoints(Calendar calendar) {
        // We only have historical data at the moment, so show the past 3 days
        Calendar fromCalendar = (Calendar)calendar.clone();
        fromCalendar.add(Calendar.DATE, -3);

        List<DataPoint> dataPoints;

        try {
            dataPoints = this.historicalDataProvider.getDataPoints(fromCalendar, calendar);
        } catch (StorageException | InvalidKeyException | URISyntaxException | TableDoesNotExistException e) {
            throw new RuntimeException("Failed to retrieve data from historical data provider");
        }

        return dataPoints;
    }
}
