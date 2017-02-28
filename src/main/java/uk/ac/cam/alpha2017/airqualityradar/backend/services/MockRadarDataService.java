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
 * Created by henry on 27/02/2017.
 *
 * for each calendar time (every 3 hours for the next 5 days), a data point for each location (6 for predictions) are
 * returned. Each has a current air quality data point and predicted weather data point attached
 *
 *
 */
public class MockRadarDataService {

    private int daysAvailable(Calendar req) {
        Calendar max = Calendar.getInstance();
        max.add(Calendar.DATE,4);

        int numMillisecondsInDay = 1000 * 60 * 60 * 24;

        Double daysApart = Math.floor((max.getTime().getTime() - req.getTime().getTime()) / numMillisecondsInDay);

        return daysApart.intValue();
    }

    private List<Calendar> listPredictionCalendars(int measureNo, Calendar calendar){
        List<Calendar> calendarList = new LinkedList<>();

        int days = daysAvailable(calendar);
        System.out.println("days " + days);

        if (days < 0) {
            return calendarList;
        }

        for (int i = measureNo; i < 8; i++) {
            calendarList.add(Calendar.getInstance());
            Calendar newCal = calendarList.get(calendarList.size()-1);
            newCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            newCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            newCal.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            newCal.set(Calendar.HOUR_OF_DAY, i*3);
        }

        for (int i = 0; i < days; i++) {
            for(int j = 1; j < 8; j++) {
                calendarList.add(Calendar.getInstance());
                Calendar newCal = calendarList.get(calendarList.size()-1);
                newCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                newCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                newCal.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+i+1);
                newCal.set(Calendar.HOUR_OF_DAY, j*3);

            }
        }

        return calendarList;
    }

    private List<Location> listLocations(String filename) throws FileNotFoundException {
        Scanner csvScanner = new Scanner(new File(filename));
        List<Location> locations = new LinkedList<>();
        csvScanner.nextLine();
        while (csvScanner.hasNext()) {
            String[] data = csvScanner.nextLine().split(",");
            locations.add(new Location(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
        }
        return locations;
    }

    public List<DataPoint> getDataPoints(Calendar calendar) throws FileNotFoundException {
        List<DataPoint> dataPoints = new ArrayList<>();
        List<Calendar> calendarList;
        List<Location> locationList;

        // Get calendars
        int requestMin = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        int measureNo = (requestMin / 180) + 1;
        calendarList = listPredictionCalendars(measureNo, calendar);

        // Get locations
        locationList = listLocations("src/main/java/uk/ac/cam/alpha2017/airqualityradar/backend/services/airPointLocationFile.csv");

        for (int i = 0; i < calendarList.size(); i++) {
            Calendar sampleCalendar = calendarList.get(i);

            // Make weather data point — weather is global across Cambridge for our purposes
            TemperatureMeasurement temp = new TemperatureMeasurement(ThreadLocalRandom.current().nextDouble(0,20), "ºC");
            HumidityMeasurement hum = new HumidityMeasurement(ThreadLocalRandom.current().nextDouble(60,90), "%");
            WindDirectionMeasurement windD = new WindDirectionMeasurement("NE");
            WindSpeedMeasurement windS = new WindSpeedMeasurement(ThreadLocalRandom.current().nextDouble(5,15), "m/s");
            PressureMeasurement press = new PressureMeasurement(ThreadLocalRandom.current().nextDouble(900,1000), "mb");
            RainfallMeasurement rain = new RainfallMeasurement(ThreadLocalRandom.current().nextDouble(0,10), "mm");

            WeatherDataPoint weatherDataPoint = new WeatherDataPoint(temp, hum, windD, windS, press, rain);

            for (int j = 0; j < locationList.size(); j++) {
                Location sampleLocation = locationList.get(j);

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
