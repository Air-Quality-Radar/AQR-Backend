package uk.ac.cam.alpha2017.airqualityradar.backend.data.entities;

import com.microsoft.azure.storage.table.StoreAs;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Creates a WeatherDataEntity which will be instantiated by reflection
 */
public class WeatherDataEntity extends TableServiceEntity {
    private String searchTimestamp;
    private String year;
    private String daysSinceStartOfYear;
    private String minutesPastMidnight;
    private Long latitude;
    private Long longitude;
    private String temperature;
    private String humidity;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String rainfallInPastHour;

    @StoreAs(name = WeatherDataEntityColumns.SEARCH_TIMESTAMP)
    public void setSearchTimestamp(String searchTimestamp) {
        this.searchTimestamp = searchTimestamp;
    }

    @StoreAs(name = WeatherDataEntityColumns.YEAR)
    public void setYear(String year) {
        this.year = year;
    }

    @StoreAs(name = WeatherDataEntityColumns.DAYS)
    public void setDaysSinceStartOfYear(String daysSinceStartOfYear) {
        this.daysSinceStartOfYear = daysSinceStartOfYear;
    }

    @StoreAs(name = WeatherDataEntityColumns.MINUTES)
    public void setMinutesPastMidnight(String minutesPastMidnight) {
        this.minutesPastMidnight = minutesPastMidnight;
    }

    @StoreAs(name = WeatherDataEntityColumns.LATITUDE)
    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @StoreAs(name = WeatherDataEntityColumns.LONGITUDE)
    public void setLongitude(Long Longitude) {
        this.longitude = Longitude;
    }

    @StoreAs(name = WeatherDataEntityColumns.TEMPERATURE)
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @StoreAs(name = WeatherDataEntityColumns.HUMIDITY)
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @StoreAs(name = WeatherDataEntityColumns.PRESSURE)
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @StoreAs(name = WeatherDataEntityColumns.WIND_SPEED)
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @StoreAs(name = WeatherDataEntityColumns.WIND_DIRECTION)
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    @StoreAs(name = WeatherDataEntityColumns.RAINFALL)
    public void setRainfallInPastHour(String rainfallInPastHour) {
        this.rainfallInPastHour = rainfallInPastHour;
    }

    @StoreAs(name = WeatherDataEntityColumns.SEARCH_TIMESTAMP)
    public String getSearchTimestamp() {
        return searchTimestamp;
    }

    @StoreAs(name = WeatherDataEntityColumns.YEAR)
    public String getYear() {
        return year;
    }

    @StoreAs(name = WeatherDataEntityColumns.DAYS)
    public String getDaysSinceStartOfYear() {
        return daysSinceStartOfYear;
    }

    @StoreAs(name = WeatherDataEntityColumns.MINUTES)
    public String getMinutesPastMidnight() {
        return minutesPastMidnight;
    }

    @StoreAs(name = WeatherDataEntityColumns.LATITUDE)
    public Long getLatitude() {
        return latitude;
    }

    @StoreAs(name = WeatherDataEntityColumns.LONGITUDE)
    public Long getLongitude() {
        return longitude;
    }

    @StoreAs(name = WeatherDataEntityColumns.TEMPERATURE)
    public String getTemperature() {
        return temperature;
    }

    @StoreAs(name = WeatherDataEntityColumns.HUMIDITY)
    public String getHumidity() {
        return humidity;
    }

    @StoreAs(name = WeatherDataEntityColumns.PRESSURE)
    public String getPressure() {
        return pressure;
    }

    @StoreAs(name = WeatherDataEntityColumns.WIND_SPEED)
    public String getWindSpeed() {
        return windSpeed;
    }

    @StoreAs(name = WeatherDataEntityColumns.WIND_DIRECTION)
    public String getWindDirection() {
        return windDirection;
    }

    @StoreAs(name = WeatherDataEntityColumns.RAINFALL)
    public String getRainfallInPastHour() {
        return rainfallInPastHour;
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += "Temperature " + temperature;
        returnString += "Humidity " + humidity;
        returnString += "Pressure " + pressure;
        returnString += "Wind speed " + windSpeed;
        returnString += "Wind direction " + windDirection;
        returnString += "Rainfall " + rainfallInPastHour;
        return returnString;
    }

}
