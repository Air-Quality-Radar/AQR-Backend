package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a DataRowEntity which should be instantiated by reflection - that's why data fields don't follow
 * Java's variable naming convention -> it follows the table schema instead.
 */
public class DataRowEntity extends TableServiceEntity {

    private Long year;
    private Long daysSinceStartOfYear;
    private Long minutesPastMidnight;
    private Long Latitude;
    private Long Longitude;
    private String NOx;
    private String PM10;
    private String PM25;

    public DataRowEntity(Long Year, Long Day, Long minutesPastMidnight, Long Latitude, Long Longitude, String NOx, String PM10, String PM25) {
        this.partitionKey = Year.toString();
        this.rowKey = String.format("%s,%s,%s,%s,%s", Year, Day, minutesPastMidnight, Latitude, Longitude);
        this.year = Year;
        this.daysSinceStartOfYear = Day;
        this.minutesPastMidnight = minutesPastMidnight;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.NOx = NOx;
        this.PM10 = PM10;
        this.PM25 = PM25;
    }

    public DataRowEntity() {
    }

    @StoreAs(name=DataRowEntityColumns.YEAR)
    public void setYear(Long year) {
        this.year = year;
    }

    @StoreAs(name=DataRowEntityColumns.DAYS)
    public void setDaysSinceStartOfYear(Long daysSinceStartOfYear) {
        this.daysSinceStartOfYear = daysSinceStartOfYear;
    }

    @StoreAs(name=DataRowEntityColumns.MINUTES)
    public void setMinutesPastMidnight(Long minutesPastMidnight) {
        this.minutesPastMidnight = minutesPastMidnight;
    }

    public void setLatitude(Long latitude) {
        Latitude = latitude;
    }

    public void setLongitude(Long Longitude) {
        this.Longitude = Longitude;
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

    @StoreAs(name=DataRowEntityColumns.YEAR)
    public Long getYear() {
        return year;
    }

    @StoreAs(name=DataRowEntityColumns.DAYS)
    public Long getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    @StoreAs(name=DataRowEntityColumns.MINUTES)
    public Long getMinutesPastMidnight() {
        return minutesPastMidnight;
    }

    public Long getLatitude() {
        return Latitude;
    }

    public Long getLongitude() {
        return Longitude;
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