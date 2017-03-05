package uk.ac.cam.alpha2017.airqualityradar.backend.data;

import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.DataPointConverter;

public class PredictionsDataProvider extends AbstractDataProvider {
    public PredictionsDataProvider(AzureTableConnector connector, DataPointConverter dataPointConverter) {
        super(connector, dataPointConverter);
    }

    @Override
    String getAirDataTableName() {
        return "predictions";
    }

    @Override
    String getWeatherDataTableName() {
        //TODO: Add weather table name
        return null;
    }
}
