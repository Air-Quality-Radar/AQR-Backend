package uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableServiceEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials.StorageConnectionInfo;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.CalendarConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.AirDataEntityColumns;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.Iterator;

public class AzureTableConnector {
    private StorageConnectionInfo connectionInfo;

    public AzureTableConnector(StorageConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    private CloudTable getCloudTable(String tableName) throws URISyntaxException, InvalidKeyException, StorageException, TableDoesNotExistException {
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(connectionInfo.getStorageConnectionString());

        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        CloudTable cloudTable = tableClient.getTableReference(tableName);

        if (!(cloudTable.exists())) {
            throw new TableDoesNotExistException(tableName);
        }

        return cloudTable;
    }

    public <T extends TableServiceEntity> Iterator<T> getEntitiesBetweenCalendars(String tableName, Calendar fromDate, Calendar toDate, Class<T> classToMapByReflection) throws StorageException, URISyntaxException, InvalidKeyException, TableDoesNotExistException {
        CloudTable cloudTable = getCloudTable(tableName);

        String fromDateCondition = TableQuery.generateFilterCondition(
                AirDataEntityColumns.SEARCH_TIMESTAMP,
                TableQuery.QueryComparisons.GREATER_THAN_OR_EQUAL,
                CalendarConverter.getSearchTimestampForCalendar(fromDate));

        String toDateCondition = TableQuery.generateFilterCondition(
                AirDataEntityColumns.SEARCH_TIMESTAMP,
                TableQuery.QueryComparisons.LESS_THAN_OR_EQUAL,
                CalendarConverter.getSearchTimestampForCalendar(toDate));

        String filterCondition = TableQuery.combineFilters(
                fromDateCondition,
                TableQuery.Operators.AND,
                toDateCondition);

        TableQuery<T> query =
                TableQuery.from(classToMapByReflection)
                          .where(filterCondition);

        Iterable<T> entityIterable = cloudTable.execute(query);

        return entityIterable.iterator();
    }
}
