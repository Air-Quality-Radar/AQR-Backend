package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.HistoricalDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.PredictionsDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.List;

public class RadarDataService implements DataService {

    private final HistoricalDataProvider historicalDataProvider;
    private final PredictionsDataProvider predictionsDataProvider;

    public RadarDataService(HistoricalDataProvider historicalDataProvider, PredictionsDataProvider predictionsDataProvider) {
        this.historicalDataProvider = historicalDataProvider;
        this.predictionsDataProvider = predictionsDataProvider;
    }

    public List<DataPoint> getDataPoints(Calendar calendar) {
        // Past three days
        Calendar fromCalendar = (Calendar)calendar.clone();
        fromCalendar.add(Calendar.DATE, -3);

        // Next three days
        Calendar toCalendar = (Calendar)calendar.clone();
        toCalendar.add(Calendar.DATE, 4);

        List<DataPoint> dataPoints;

        try {
            dataPoints = this.historicalDataProvider.getDataPoints(fromCalendar, toCalendar);
        } catch (StorageException | InvalidKeyException | URISyntaxException | TableDoesNotExistException e) {
            throw new RuntimeException("Failed to retrieve data from historical data provider");
        }

        if (dataPoints.size() > 0) {
            DataPoint lastDataPoint = dataPoints.get(dataPoints.size() - 1);
            fromCalendar = lastDataPoint.getCalendar();
        }

        try {
            dataPoints.addAll(this.predictionsDataProvider.getDataPoints(fromCalendar, toCalendar));
        } catch (StorageException | InvalidKeyException | URISyntaxException | TableDoesNotExistException e) {
            throw new RuntimeException("Failed to retrieve data from predictions data provider");
        }

        return dataPoints;
    }
}
