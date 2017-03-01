package uk.ac.cam.alpha2017.airqualityradar.backend.data;

// Include the following imports to use table APIs

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.entities.DataRowEntityColumns;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NumberMeasurement;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static uk.ac.cam.alpha2017.airqualityradar.backend.data.StorageConnectionInfo.storageConnectionString;

public class Testing {
    // Define the connection-string with your values.
    public static Calendar toCalendar(String dateString) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static void printEntity(DataRowEntity entity) {
        System.out.println("The search timestamp is " + entity.getSearchTimestamp());
        System.out.println("The year is " + entity.getYear());
        System.out.println("The minute is " + entity.getMinutesPastMidnight());
        System.out.println("The day is " + entity.getDaysSinceStartOfYear());
        System.out.println("The longitude is " + entity.getLongitude());
        System.out.println("The latitude is " + entity.getLatitude());
        System.out.println("Nox is " + entity.getNOx());
        System.out.println("PM10 is " + entity.getPM10());
        System.out.println("PM25 is " + entity.getPM25());

        System.out.println(entity.getRowKey() + ": " + String.format("%s,%s,%s", entity.getNOx(), entity.getPM10(), entity.getPM25()));
    }

    public static void testBetweenCalendars() throws ParseException, StorageException, TableDoesNotExistException, InvalidKeyException, URISyntaxException {
        AzureTableConnector connector = new AzureTableConnector();

        Calendar cal1 = toCalendar("2016-01-09 16:00:00");
        Calendar cal2 = toCalendar("2016-01-10 16:00:00");

        Iterator<DataRowEntity> result = connector.getEntitiesBetweenCalendars("pollution", cal1, cal2);

        result.forEachRemaining(entity -> printEntity(entity));
    }

    public static void main(String[] args) throws ParseException, StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        listTables();
        getEntities();

        HistoricalDataProvider hdp = new HistoricalDataProvider();

        Calendar cal1 = toCalendar("2016-01-09 16:00:00");
        Calendar cal2 = toCalendar("2016-01-10 16:00:00");

        List<Calendar> calList = new LinkedList<>();
        calList.add(cal1);
        calList.add(cal2);

        Location loc1 = new Location(52214239.0, 136262.0);
        Location loc2 = new Location(52214239, 136262);

        List<Location> locList = new LinkedList<>();

        locList.add(loc1);
        locList.add(loc2);

        for (DataPoint x : hdp.getDataPoints(calList, locList)) {

            AirDataPoint adp = x.getAir();
            System.out.printf("%s,%s,%s\n", printNumMes(adp.getNox()),printNumMes(adp.getPm10()),printNumMes(adp.getPm25()));
        }
    }

    public static String printNumMes (NumberMeasurement numMes) {
        if (numMes==null) {return "null";}
        else return String.format("%s",numMes.getValue());
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

    public static void getEntities() throws URISyntaxException, InvalidKeyException, StorageException {
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(storageConnectionString);

        // Create the table client.
        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        // Create a cloud table object for the table.
        CloudTable cloudTable = tableClient.getTableReference("pollution");

        //table exists
        System.out.println(cloudTable.exists());


        // UP UNTIL NOW JUST AZURE TUTORIAL CODE
        // Generate a filter for Timestamp>0 => should always return something
        String partitionFilter = TableQuery.generateFilterCondition(DataRowEntityColumns.ROW_KEY, QueryComparisons.GREATER_THAN_OR_EQUAL, "2017,9,960");


        // Specify a partition query, using "Timestamp>0" as the partition key filter.
        TableQuery<DataRowEntity> partitionQuery =
                TableQuery.from(DataRowEntity.class)
                        .where(partitionFilter);

        Iterable<DataRowEntity> entityIterable = cloudTable.execute(partitionQuery);

        // Loop through the results, displaying information about the entity.
        for (DataRowEntity entity : entityIterable) {
            printEntity(entity);
        }
    }
}
