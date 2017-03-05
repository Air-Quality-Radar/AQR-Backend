package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a AirDataEntity which will be instantiated by reflection
 */
public class AirDataEntity extends TableServiceEntity implements Comparable {

    private String searchTimestamp;
    private String year;
    private String daysSinceStartOfYear;
    private String minutesPastMidnight;
    private Long latitude;
    private Long longitude;
    private String NOx;
    private String PM10;
    private String PM25;

    @StoreAs(name = AirDataEntityColumns.SEARCH_TIMESTAMP)
    public void setSearchTimestamp(String searchTimestamp) {
        this.searchTimestamp = searchTimestamp;
    }

    @StoreAs(name = AirDataEntityColumns.YEAR)
    public void setYear(String year) {
        this.year = year;
    }

    @StoreAs(name = AirDataEntityColumns.DAYS)
    public void setDaysSinceStartOfYear(String daysSinceStartOfYear) {
        this.daysSinceStartOfYear = daysSinceStartOfYear;
    }

    @StoreAs(name = AirDataEntityColumns.MINUTES)
    public void setMinutesPastMidnight(String minutesPastMidnight) {
        this.minutesPastMidnight = minutesPastMidnight;
    }

    @StoreAs(name = AirDataEntityColumns.LATITUDE)
    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @StoreAs(name = AirDataEntityColumns.LONGITUDE)
    public void setLongitude(Long Longitude) {
        this.longitude = Longitude;
    }

    @StoreAs(name = AirDataEntityColumns.NOX)
    public void setNOx(String NOx) {
        this.NOx = NOx;
    }

    @StoreAs(name = AirDataEntityColumns.PM10)
    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    @StoreAs(name = AirDataEntityColumns.PM25)
    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    @StoreAs(name = AirDataEntityColumns.SEARCH_TIMESTAMP)
    public String getSearchTimestamp() {
        return searchTimestamp;
    }

    @StoreAs(name = AirDataEntityColumns.YEAR)
    public String getYear() {
        return year;
    }

    @StoreAs(name = AirDataEntityColumns.DAYS)
    public String getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    @StoreAs(name = AirDataEntityColumns.MINUTES)
    public String getMinutesPastMidnight() {
        return minutesPastMidnight;
    }

    @StoreAs(name = AirDataEntityColumns.LATITUDE)
    public Long getLatitude() {
        return latitude;
    }

    @StoreAs(name = AirDataEntityColumns.LONGITUDE)
    public Long getLongitude() {
        return longitude;
    }

    @StoreAs(name = AirDataEntityColumns.NOX)
    public String getNOx() {
        return NOx;
    }

    @StoreAs(name = AirDataEntityColumns.PM10)
    public String getPM10() {
        return PM10;
    }

    @StoreAs(name = AirDataEntityColumns.PM25)
    public String getPM25() {
        return PM25;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof AirDataEntity)) {
            throw new ClassCastException("AirDataEntity expected");
        }
        return this.getSearchTimestamp().compareTo(((AirDataEntity) o).getSearchTimestamp());
    }
}
