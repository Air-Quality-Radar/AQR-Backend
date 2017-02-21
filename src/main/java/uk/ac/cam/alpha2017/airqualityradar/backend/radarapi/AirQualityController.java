package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.util.List;

@RestController
public class AirQualityController {

    @RequestMapping(RadarAPIEndpoints.AirQualityEndpoint)
    public List<DataPoint> airQuality() {
        return null;
    }
}
