package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import java.util.Calendar;

public class MetOfficeRecord {
    private Calendar calendar;
    private int sigWeatherCode;
    private String visibility;
    private float temperature;
    private float windSpeed;
    private float precipitationProb;
    private float humidity;
    private float windGust;
    private String windDirection;

    public MetOfficeRecord(Calendar calendar, int sigWeatherCode, String visibility, float temperature, float windSpeed, float precipitationProb, float humidity, float windGust, String windDirection) {
        this.calendar = calendar;
        this.sigWeatherCode = sigWeatherCode;
        this.visibility = visibility;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.precipitationProb = precipitationProb;
        this.humidity = humidity;
        this.windGust = windGust;
        this.windDirection = windDirection;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getSigWeatherCode() {
        return sigWeatherCode;
    }

    public String getVisibility() {
        return visibility;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getPrecipitationProb() {
        return precipitationProb;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWindGust() {
        return windGust;
    }

    public String getWindDirection() {
        return windDirection;
    }
}
