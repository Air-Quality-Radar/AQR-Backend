package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PredictionsDataProvider {

    /**
     * Gets predictions from the ML model for each of the locations at each of the specified times,
     * given the weather forecasts at those times
     *
     * @param forecasts A map of date/times we wish to get the prediction for and the
     *                  associated weather forecast at that date/time
     * @param locations The locations at which we are requesting predictions
     * @return The data points predicted by the ML model per calendar per location
     */
    public List<DataPoint> getPredictions(Map<Calendar, WeatherDataPoint> forecasts, List<Location> locations) {
        return null;
    }
}
