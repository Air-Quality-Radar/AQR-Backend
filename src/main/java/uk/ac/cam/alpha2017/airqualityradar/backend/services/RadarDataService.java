package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import uk.ac.cam.alpha2017.airqualityradar.backend.data.HistoricalDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.PredictionsDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RadarDataService {

    public List<DataPoint> getDataPoints(Calendar calendar) throws FileNotFoundException {
        //It should return a list of data points for a given day for appropriate locations and times
        //calendar is the day we want info for
        List<DataPoint> datapoints;
        Calendar now = Calendar.getInstance();


        if(now.after(calendar)){
            HistoricalDataProvider historicalProvider = new HistoricalDataProvider();

            List<Calendar> calendarList = new LinkedList<>();

            //get list of locations
            List<Location> locationList = new LinkedList<>();
            Scanner csvScanner = new Scanner(new File("src/main/java/uk/ac/cam/alpha2017/airqualityradar/backend/services/airPointLocationFile.csv"));
            csvScanner.nextLine();
            while(csvScanner.hasNext()){
                String[] data = csvScanner.nextLine().split(",");
                locationList.add(new Location(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
            }

            //list of calendars, list of locations
            datapoints = historicalProvider.getDataPoints(calendarList, locationList);


        } else {
            PredictionsDataProvider predictionProvider = new PredictionsDataProvider();

            //get all calendars - every 3 hours for the requested day and however many are available after
            List<Calendar> calendarList = new LinkedList<>();
            int days = 3;
            for(int i=0; i<days; i++){
                Calendar addCal = calendar;
                addCal.add(Calendar.DATE, i);
                for(int j=0; j<8; j++) {
                    addCal.set(Calendar.HOUR_OF_DAY, j*3);
                    calendarList.add(addCal);
                }
            }

            Iterator k = calendarList.iterator();
            while(k.hasNext()){
                Calendar c = (Calendar)k.next();
                System.out.println(c.get(Calendar.DATE) + " " + c.get(Calendar.HOUR_OF_DAY));
            }

            List<WeatherDataPoint> forecastList = new LinkedList<>();
            for(int i=0; i< calendarList.size(); i++){
                //forecastList.add(new WeatherDataPoint());
            }

            //create mapping
            Map<Calendar, WeatherDataPoint> mapping = new HashMap<>();
            Iterator i1 = calendarList.iterator();
            Iterator i2 = forecastList.iterator();
            while(i1.hasNext() && i2.hasNext()) {
                mapping.put((Calendar)i1.next(), (WeatherDataPoint)i2.next());
            }

            //get list of locations
            List<Location> locationList = new LinkedList<>();
            Scanner csvScanner = new Scanner(new File("src/main/java/uk/ac/cam/alpha2017/airqualityradar/backend/services/predictionLocationFile.csv"));
            csvScanner.nextLine();
            while(csvScanner.hasNext()){
                String[] data = csvScanner.nextLine().split(",");
                locationList.add(new Location(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
            }

            //mapping between days and weather forecasts, list of locations
            datapoints = predictionProvider.getPredictions(mapping, locationList);

        }

        //create mapping
//            Map<Calendar, WeatherDataPoint> mapping = new HashMap<>();
//            Iterator i1 = calendarList.iterator();
//            Iterator i2 = weatherList.iterator();
//            while(i1.hasNext() && i2.hasNext()) {
//                mapping.put((Calendar)i1.next(), (WeatherDataPoint)i2.next());
//            }

//            PredictionsDataProvider predictionProvider = new PredictionsDataProvider();
//            dataPoints = predictionProvider.getPredictions(mapping, locationList);


        return datapoints;
    }

    public static void main(String[] args) throws FileNotFoundException {
        RadarDataService rds = new RadarDataService();
        Calendar testCal = Calendar.getInstance();
        testCal.add(Calendar.DATE,3);
        rds.getDataPoints(testCal);

    }
}
