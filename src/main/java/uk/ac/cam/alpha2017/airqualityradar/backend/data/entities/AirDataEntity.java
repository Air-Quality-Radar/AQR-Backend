package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a AirDataEntity which will be instantiated by reflection
 */
public class AirDataEntity extends TableServiceEntity implements Comparable {

    private String searchTimestamp;
    private Integer year;
    private Integer daysSinceStartOfYear;
    private Integer minutesPastMidnight;
    private Long latitude;
    private Long longitude;
    private Double NOx;
    private Double PM10;
    private Double PM25;

    public AirDataEntity() {}

    public AirDataEntity(String partitionKey, String rowKey, String searchTimestamp, Integer year, Integer daysSinceStartOfYear, Integer minutesPastMidnight, Long latitude, Long longitude, Double NOx, Double PM10, Double PM25) {
        super(partitionKey, rowKey);
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

    public String getSearchTimestamp() {
        return searchTimestamp;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    public Integer getMinutesPastMidnight() {
        return minutesPastMidnight;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Double getNOx() {
        return NOx;
    }

    public Double getPM10() {
        return PM10;
    }

    public Double getPM25() {
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
