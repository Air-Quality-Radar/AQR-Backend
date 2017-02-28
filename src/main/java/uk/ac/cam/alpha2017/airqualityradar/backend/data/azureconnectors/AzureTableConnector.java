package uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableQuery;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import static uk.ac.cam.alpha2017.airqualityradar.backend.data.StorageConnectionInfo.storageConnectionString;

/**
 * Created by jirka on 25.2.17.
 */
public class AzureTableConnector {

    private CloudTable getCloudTable(String tableName) throws URISyntaxException, InvalidKeyException, StorageException {
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(storageConnectionString);

        // Create the table client.
        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        // Create a cloud table object for the table.
        CloudTable cloudTable = tableClient.getTableReference(tableName);

        return cloudTable;
    }

    public DataRowEntity getEntityFromTableByRowKey(String tableName, String rowKey) throws StorageException, URISyntaxException, InvalidKeyException {
        // Define constant for filters.
        final String ROW_KEY = "RowKey";

        //get cloud table
        CloudTable cloudTable = getCloudTable(tableName);

        if (!(cloudTable.exists()))
            throw new IllegalArgumentException(String.format("Table %s doesn't exist", tableName));

        //Create filter
        String partitionFilter = TableQuery.generateFilterCondition(ROW_KEY, TableQuery.QueryComparisons.EQUAL, rowKey);


        // Specify query
        TableQuery<DataRowEntity> partitionQuery =
                TableQuery.from(DataRowEntity.class)
                        .where(partitionFilter);

        //Execute query
        Iterable<DataRowEntity> entityIterable = cloudTable.execute(partitionQuery); //executes fine

        return entityIterable.iterator().next();
    }

//    public Iterable<DataRowEntity> getEntitiesBetweenDates(String tableName, )
}
