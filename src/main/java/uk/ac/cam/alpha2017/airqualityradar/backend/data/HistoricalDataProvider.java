package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;

import java.util.Calendar;
import java.util.List;

public class HistoricalDataProvider {

    /**
     * Gets a historical data points from the collection of historical data on Azure for
     * each of the specified date/times, at each of the specified locations
     *
     * @param calendars The date/times we are requesting data points for
     * @param locations The locations we are requesting data points for
     * @return The data points from the historical data per calendar per location
     */
    public List<DataPoint> getDataPoints(List<Calendar> calendars, List<Location> locations) {
        return null;
    }
}
