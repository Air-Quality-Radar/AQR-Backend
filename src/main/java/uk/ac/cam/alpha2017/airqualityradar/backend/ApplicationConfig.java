package uk.ac.cam.alpha2017.airqualityradar.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.HistoricalDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials.StorageConnectionInfo;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials.StorageCredentials;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.DataPointConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.radarapi.AirQualityController;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.DataService;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.RadarDataService;

import java.io.IOException;

@Configuration
public class ApplicationConfig {
    @Bean
    public AirQualityController airQualityController() {
        return new AirQualityController(dataService());
    }

    @Bean
    public DataService dataService() {
        return new RadarDataService(historicalDataProvider());
    }

    @Bean
    public HistoricalDataProvider historicalDataProvider() {
        return new HistoricalDataProvider(azureTableConnector(), dataPointConverter());
    }

    @Bean
    public AzureTableConnector azureTableConnector() {
        StorageConnectionInfo connectionInfo;
        connectionInfo = storageCredentials().getConnectionInfo();

        return new AzureTableConnector(connectionInfo);
    }

    @Bean
    public StorageCredentials storageCredentials() {
        return new StorageCredentials();
    }

    @Bean
    DataPointConverter dataPointConverter() {
        return new DataPointConverter();
    }
}
