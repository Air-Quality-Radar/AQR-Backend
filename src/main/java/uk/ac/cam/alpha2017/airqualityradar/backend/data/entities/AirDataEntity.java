package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.CalendarConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM10Measurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM25Measurement;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Creates a AirDataEntity which will be instantiated by reflection
 */
public class AirDataEntity extends TableServiceEntity {

    private String searchTimestamp;
    private String year;
    private String daysSinceStartOfYear;
    private String minutesPastMidnight;
    private Long latitude;
    private Long longitude;
    private String NOx;
    private String PM10;
    private String PM25;

    @StoreAs(name= AirDataEntityColumns.SEARCH_TIMESTAMP)
    public void setSearchTimestamp(String searchTimestamp) {
        this.searchTimestamp = searchTimestamp;
    }

    @StoreAs(name= AirDataEntityColumns.YEAR)
    public void setYear(String year) {
        this.year = year;
    }

    @StoreAs(name= AirDataEntityColumns.DAYS)
    public void setDaysSinceStartOfYear(String daysSinceStartOfYear) {
        this.daysSinceStartOfYear = daysSinceStartOfYear;
    }

    @StoreAs(name= AirDataEntityColumns.MINUTES)
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

    @StoreAs(name= AirDataEntityColumns.SEARCH_TIMESTAMP)
    public String getSearchTimestamp() {
        return searchTimestamp;
    }

    @StoreAs(name= AirDataEntityColumns.YEAR)
    public String getYear() {
        return year;
    }

    @StoreAs(name= AirDataEntityColumns.DAYS)
    public String getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    @StoreAs(name= AirDataEntityColumns.MINUTES)
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