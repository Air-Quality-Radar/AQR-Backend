package uk.ac.cam.alpha2017.airqualityradar.backend.data;

// Include the following imports to use table APIs

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;

import java.util.Calendar;
import java.util.Date;

import static uk.ac.cam.alpha2017.airqualityradar.backend.data.StorageConnectionInfo.storageConnectionString;

public class Testing {
    // Define the connection-string with your values.


    public static void main(String[] args) {
        listTables();
        Calendar calendar = new Calendar(new Date(""))
//        getEntities();
    }

    public static void listTables() {
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Loop through the collection of table names.
            for (String table : tableClient.listTables()) {
                // Output each table name.
                System.out.println(table);
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public static void getEntities() {
        try {
            // Define constants for filters.
            final String ROW_KEY = "RowKey";

            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Create a cloud table object for the table.
            CloudTable cloudTable = tableClient.getTableReference("rawpollution");

            //table exists
            System.out.println(cloudTable.exists());


            // UP UNTIL NOW JUST AZURE TUTORIAL CODE
            // Generate a filter for Timestamp>0 => should always return something
            String partitionFilter = TableQuery.generateFilterCondition(ROW_KEY, QueryComparisons.EQUAL, "2017,9,960,52214239,136262");


            // Specify a partition query, using "Timestamp>0" as the partition key filter.
            TableQuery<DataRowEntity> partitionQuery =
                    TableQuery.from(DataRowEntity.class)
                            .where(partitionFilter);

            Iterable<DataRowEntity> entityIterable = cloudTable.execute(partitionQuery); //executes fine

            // Loop through the results, displaying information about the entity.
            // FAILS
            for (DataRowEntity entity : entityIterable) {
                System.out.println(entity.getRowKey());
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
}
