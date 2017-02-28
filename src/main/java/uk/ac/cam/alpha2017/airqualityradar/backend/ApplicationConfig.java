package uk.ac.cam.alpha2017.airqualityradar.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.cam.alpha2017.airqualityradar.backend.radarapi.AirQualityController;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.DataService;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.MockRadarDataService;

@Configuration
public class ApplicationConfig {
    @Bean
    public AirQualityController airQualityController() {
        return new AirQualityController(dataService());
    }

    @Bean
    public DataService dataService() {
        return new MockRadarDataService();
    }
}
