package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

/**
 * Created by jirka on 2.3.17.
 */
public interface DataEntityColumns {
    String ROW_KEY = "RowKey";
    String PARTITION_KEY = "PartitionKey";

    /**
     * The year, formatted as a 4-digit number
     * e.g. 2012
     */
    String YEAR = "Year";

    /**
     * The number of days since the start of the year
     * e.g. January 1st is 0
     */
    String DAYS = "Days";

    /**
     * The number of minutes since midnight
     * e.g. 00:34 is 34
     */
    String MINUTES = "Minutes";

    /**
     * The longitude and latitude of where data point stored as long,
     * e.g. 52.210925 is 52210925
     */
    String LATITUDE = "Latitude";
    String LONGITUDE = "Longitude";

    /**
     * The timestamp for the data point
     */
    String SEARCH_TIMESTAMP = "SearchTimestamp";
}
