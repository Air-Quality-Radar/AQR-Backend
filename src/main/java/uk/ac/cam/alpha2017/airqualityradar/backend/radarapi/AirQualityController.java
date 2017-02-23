package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.RadarDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class AirQualityController {
    private RadarDataService dataService;

    public AirQualityController(RadarDataService dataService) {
        this.dataService = dataService;
    }

    /**
     * This is called by the Spring framework when the web client calls the air quality endpoint
     * It should return a list of data points for a given day for appropriate locations and times
     * @param day The day to get data for
     * @return A list of data points for that day at the various locations and times
     * @throws ParseException When an invalid date is passed to the API, a ParseException will be thrown
     */
    @RequestMapping(RadarAPIEndpoints.AirQualityEndpoint)
    public List<DataPoint> airQuality(@PathVariable("day") String day) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return dataService.getDataPoints(calendar);
    }
}
