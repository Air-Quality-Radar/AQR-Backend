package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a DataRowEntity which should be instantiated by reflection - that's why data fields don't follow
 * Java's variable naming convention -> it follows the table schema instead.
 */
public class DataRowEntity extends TableServiceEntity {

    private String searchTimestamp;
    private String year;
    private String daysSinceStartOfYear;
    private String minutesPastMidnight;
    private Long latitude;
    private Long longitude;
    private String NOx;
    private String PM10;
    private String PM25;

    public DataRowEntity(String searchTimestamp, String year, String daysSinceStartOfYear, String minutesPastMidnight, Long latitude, Long longitude, String NOx, String PM10, String PM25) {
        this.searchTimestamp = searchTimestamp;
        this.year = year;
        this.daysSinceStartOfYear = daysSinceStartOfYear;
        this.minutesPastMidnight = minutesPastMidnight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.NOx = NOx;
        this.PM10 = PM10;
        this.PM25 = PM25;
    }

    public DataRowEntity() {
    }

    @StoreAs(name=DataRowEntityColumns.SEARCH_TIMESTAMP)
    public void setSearchTimestamp(String searchTimestamp) {
        this.searchTimestamp = searchTimestamp;
    }

    @StoreAs(name=DataRowEntityColumns.YEAR)
    public void setYear(String year) {
        this.year = year;
    }

    @StoreAs(name=DataRowEntityColumns.DAYS)
    public void setDaysSinceStartOfYear(String daysSinceStartOfYear) {
        this.daysSinceStartOfYear = daysSinceStartOfYear;
    }

    @StoreAs(name=DataRowEntityColumns.MINUTES)
    public void setMinutesPastMidnight(String minutesPastMidnight) {
        this.minutesPastMidnight = minutesPastMidnight;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Long Longitude) {
        this.longitude = Longitude;
    }

    public void setNOx(String NOx) {
        this.NOx = NOx;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    @StoreAs(name=DataRowEntityColumns.SEARCH_TIMESTAMP)
    public String getSearchTimestamp() {
        return searchTimestamp;
    }

    @StoreAs(name=DataRowEntityColumns.YEAR)
    public String getYear() {
        return year;
    }

    @StoreAs(name=DataRowEntityColumns.DAYS)
    public String getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    @StoreAs(name=DataRowEntityColumns.MINUTES)
    public String getMinutesPastMidnight() {
        return minutesPastMidnight;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public String getNOx() {
        return NOx;
    }

    public String getPM10() {
        return PM10;
    }

    public String getPM25() {
        return PM25;
    }
}