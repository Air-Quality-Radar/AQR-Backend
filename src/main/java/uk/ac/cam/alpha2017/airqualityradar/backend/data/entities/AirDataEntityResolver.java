package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.EntityProperty;
import com.microsoft.azure.storage.table.EntityResolver;

import java.util.Date;
import java.util.HashMap;

public class AirDataEntityResolver implements EntityResolver<AirDataEntity> {
    @Override
    public AirDataEntity resolve(String partitionKey, String rowKey, Date timeStamp, HashMap<String, EntityProperty> properties, String etag) throws StorageException {
        EntityProperty nox = properties.get(AirDataEntityColumns.NOX);
        EntityProperty pm10 = properties.get(AirDataEntityColumns.PM10);
        EntityProperty pm25 = properties.get(AirDataEntityColumns.PM25);

        return new AirDataEntity(
                partitionKey,
                rowKey,
                properties.get(AirDataEntityColumns.SEARCH_TIMESTAMP).getValueAsString(),
                properties.get(AirDataEntityColumns.YEAR).getValueAsInteger(),
                properties.get(AirDataEntityColumns.DAYS).getValueAsInteger(),
                properties.get(AirDataEntityColumns.MINUTES).getValueAsInteger(),
                properties.get(AirDataEntityColumns.LATITUDE).getValueAsLong(),
                properties.get(AirDataEntityColumns.LONGITUDE).getValueAsLong(),
                nox.getValueAsString().equals("") ? null : nox.getValueAsDouble(),
                pm10.getValueAsString().equals("") ? null : pm10.getValueAsDouble(),
                pm25.getValueAsString().equals("") ? null : pm25.getValueAsDouble()
        );
    }
}
