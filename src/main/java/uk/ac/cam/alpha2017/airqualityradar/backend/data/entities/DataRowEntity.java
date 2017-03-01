package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.CalendarConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.AirDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.WeatherDataPoint;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.NOxMeasurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM10Measurement;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements.PM25Measurement;

import java.text.ParseException;
import java.util.Calendar;

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

    /**
     * Creates a DataPoint from an entity, calendar and location
     *
     * @return The data points from the historical data per calendar per location
     */
    public DataPoint convertToDataPoint() {
        Calendar calendar;

        try {
            calendar = CalendarConverter.getCalendarFromSearchTimestamp(getSearchTimestamp());
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse search timestamp in entity");
        }

        Location location = new Location(getLatitude(), getLongitude());

        NOxMeasurement NOx = getNOx().equals("") ? null : new NOxMeasurement(Double.parseDouble(getNOx()));
        PM10Measurement PM10 = getPM10().equals("") ? null : new PM10Measurement(Double.parseDouble(getPM10()));
        PM25Measurement PM25 = getPM25().equals("") ? null : new PM25Measurement(Double.parseDouble(getPM25()));

        AirDataPoint airDataPoint = new AirDataPoint(NOx, PM10, PM25);
        WeatherDataPoint weatherDataPoint = new WeatherDataPoint();

        return new DataPoint(calendar, location, airDataPoint, weatherDataPoint);
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