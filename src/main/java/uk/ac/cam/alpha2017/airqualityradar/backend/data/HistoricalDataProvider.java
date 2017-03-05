package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.DataPointConverter;

public class HistoricalDataProvider extends AbstractDataProvider {

    public HistoricalDataProvider(AzureTableConnector connector, DataPointConverter dataPointConverter) {
        super(connector, dataPointConverter);
    }

    @Override
    String getAirDataTableName() {
        return "pollution";
    }


    @Override
    String getWeatherDataTableName() {
        return "weather";
    }
}
