package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * For each calendar time (every 3 hours for the next 5 days), a data point for each location (6 for predictions) are
 * returned. Each has a current air quality data point and predicted weather data point attached.
 *
 */
public class MockRadarDataService implements DataService {

    private int daysAvailable(Calendar req) {
        return 5;
    }

    private List<Calendar> predictionCalendarsAround(Calendar calendar) {
        List<Calendar> calendarList = new LinkedList<>();

        int days = daysAvailable(calendar);

        if (days < 0) {
            return calendarList;
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (int additionalHours = 0; additionalHours <= days * 24; additionalHours += 3) {
            Calendar newCalendar = (Calendar) calendar.clone();
            newCalendar.add(Calendar.HOUR_OF_DAY, additionalHours);
            calendarList.add(newCalendar);
        }

        return calendarList;
    }

    private List<Location> listLocations(String filename) {
        Scanner csvScanner;
        try {
            csvScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find required monitoring locations file");
        }
        List<Location> locations = new LinkedList<>();
        csvScanner.nextLine();
        while (csvScanner.hasNext()) {
            String[] data = csvScanner.nextLine().split(",");
            locations.add(new Location(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
        }
        return locations;
    }

    public List<DataPoint> getDataPoints(Calendar calendar) {
        List<DataPoint> dataPoints = new ArrayList<>();

        // Get calendars
        List<Calendar> calendarList = predictionCalendarsAround(calendar);

        // Get locations
        List<Location> locationList = listLocations("src/main/java/uk/ac/cam/alpha2017/airqualityradar/backend/services/airPointLocationFile.csv");

        for (Calendar sampleCalendar : calendarList) {
            // Make weather data point — weather is global across Cambridge for our purposes
            TemperatureMeasurement temp = new TemperatureMeasurement(ThreadLocalRandom.current().nextDouble(0,20), "ºC");
            HumidityMeasurement hum = new HumidityMeasurement(ThreadLocalRandom.current().nextDouble(60,90), "%");
            WindDirectionMeasurement windD = new WindDirectionMeasurement("NE");
            WindSpeedMeasurement windS = new WindSpeedMeasurement(ThreadLocalRandom.current().nextDouble(5,15), "m/s");
            PressureMeasurement press = new PressureMeasurement(ThreadLocalRandom.current().nextDouble(900,1000), "mb");
            RainfallMeasurement rain = new RainfallMeasurement(ThreadLocalRandom.current().nextDouble(0,10), "mm");

            WeatherDataPoint weatherDataPoint = new WeatherDataPoint(temp, hum, windD, windS, press, rain);

            for (Location sampleLocation : locationList) {
                // Make air data point
                NOxMeasurement nox = new NOxMeasurement(ThreadLocalRandom.current().nextDouble(0, 25));
                PM10Measurement pm10 = new PM10Measurement(ThreadLocalRandom.current().nextDouble(5, 15));
                PM25Measurement pm25 = new PM25Measurement(ThreadLocalRandom.current().nextDouble(50, 600));
                AirDataPoint airDataPoint = new AirDataPoint(nox, pm10, pm25);

                dataPoints.add(new DataPoint(sampleCalendar, sampleLocation, airDataPoint, weatherDataPoint));
            }
        }

        return dataPoints;
    }
}
