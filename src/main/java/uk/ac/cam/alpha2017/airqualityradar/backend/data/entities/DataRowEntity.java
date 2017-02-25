package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a DataRowEntity which should be instantiated by reflection - that's why data fields don't follow
 * Java's variable naming convention -> it follows the table schema instead.
 */
public class DataRowEntity extends TableServiceEntity {


    private String Year;
    private String Day;
    private String Minutes;
    private String Latitude;
    private String Longitude;
    private String NOx;
    private String PM10;
    private String PM25;

    public DataRowEntity(String Year, String Day, String Minutes, String Latitude, String Longitude, String NOx, String PM10, String PM25) {
        this.partitionKey = Year;
        this.rowKey = String.format("%s,%s,%s,%s,%s", Year, Day, Minutes, Latitude, Longitude);
        this.Year = Year;
        this.Day = Day;
        this.Minutes = Minutes;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.NOx = NOx;
        this.PM10 = PM10;
        this.PM25 = PM25;
    }

    public DataRowEntity() {

    }


    public String getYear() {
        return Year;
    }

    public String getDay() {
        return Day;
    }

    public String getMinutes() {
        return Minutes;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
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