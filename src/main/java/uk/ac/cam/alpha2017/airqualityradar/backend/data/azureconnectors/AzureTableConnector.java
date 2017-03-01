package uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableQuery;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.CalendarConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntityColumns;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.Iterator;

import static uk.ac.cam.alpha2017.airqualityradar.backend.data.StorageConnectionInfo.storageConnectionString;

public class AzureTableConnector {

    private CloudTable getCloudTable(String tableName) throws URISyntaxException, InvalidKeyException, StorageException, TableDoesNotExistException {
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(storageConnectionString);

        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        CloudTable cloudTable = tableClient.getTableReference(tableName);

        if (!(cloudTable.exists())) {
            throw new TableDoesNotExistException(tableName);
        }

        return cloudTable;
    }

    public DataRowEntity getEntityFromTableByRowKey(String tableName, String rowKey) throws StorageException, URISyntaxException, InvalidKeyException, TableDoesNotExistException {
        CloudTable cloudTable = getCloudTable(tableName);

        String filterCondition = TableQuery.generateFilterCondition(
                DataRowEntityColumns.ROW_KEY,
                TableQuery.QueryComparisons.EQUAL,
                rowKey);


        TableQuery<DataRowEntity> query =
                TableQuery.from(DataRowEntity.class)
                          .where(filterCondition);

        Iterable<DataRowEntity> entityIterable = cloudTable.execute(query);

        return entityIterable.iterator().next();
    }

    public Iterator<DataRowEntity> getEntitiesBetweenCalendars(String tableName, Calendar fromDate, Calendar toDate) throws StorageException, URISyntaxException, InvalidKeyException, TableDoesNotExistException {
        CloudTable cloudTable = getCloudTable(tableName);

        String fromDateCondition = TableQuery.generateFilterCondition(
                DataRowEntityColumns.SEARCH_TIMESTAMP,
                TableQuery.QueryComparisons.GREATER_THAN_OR_EQUAL,
                CalendarConverter.getSearchTimestampForCalendar(fromDate));

        String toDateCondition = TableQuery.generateFilterCondition(
                DataRowEntityColumns.SEARCH_TIMESTAMP,
                TableQuery.QueryComparisons.LESS_THAN_OR_EQUAL,
                CalendarConverter.getSearchTimestampForCalendar(toDate));

        String filterCondition = TableQuery.combineFilters(
                fromDateCondition,
                TableQuery.Operators.AND,
                toDateCondition);

        TableQuery<DataRowEntity> query =
                TableQuery.from(DataRowEntity.class)
                        .where(filterCondition);

        Iterable<DataRowEntity> entityIterable = cloudTable.execute(query);

        return entityIterable.iterator();
    }
}
