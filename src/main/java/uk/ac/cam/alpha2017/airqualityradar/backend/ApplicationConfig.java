package uk.ac.cam.alpha2017.airqualityradar.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.cam.alpha2017.airqualityradar.backend.radarapi.AirQualityController;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.RadarDataService;

@Configuration
public class ApplicationConfig {
    @Bean
    public AirQualityController airQualityController() {
        return new AirQualityController(radarDataService());
    }

    @Bean
    public RadarDataService radarDataService() {
        return new RadarDataService();
    }
}
