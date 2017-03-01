package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

public interface DataRowEntityColumns {
    String ROW_KEY = "RowKey";

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
     * The timestamp for the data point
     */
    String SEARCH_TIMESTAMP = "SearchTimestamp";
}
