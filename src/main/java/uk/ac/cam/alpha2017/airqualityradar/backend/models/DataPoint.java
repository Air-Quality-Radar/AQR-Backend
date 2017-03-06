package uk.ac.cam.alpha2017.airqualityradar.backend.models;

import java.util.Calendar;

public class DataPoint {
    private Calendar calendar;
    private Location location;
    private AirDataPoint air;
    private WeatherDataPoint weather;
    private boolean isPredicted;

    public DataPoint(Calendar calendar, Location location, AirDataPoint air, WeatherDataPoint weather, boolean isPredicted) {
        this.calendar = calendar;
        this.location = location;
        this.air = air;
        this.weather = weather;
        this.isPredicted = isPredicted;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Location getLocation() {
        return location;
    }

    public AirDataPoint getAir() {
        return air;
    }

    public WeatherDataPoint getWeather() {
        return weather;
    }

    public boolean isPredicted() {
        return isPredicted;
    }
}
