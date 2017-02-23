package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

public class RadarAPIEndpoints {
    // Stores the string values of the endpoints of the AQR API.

    public static final String HelloWorldEndpoint = "/hello-world";
    public static final String AirQualityEndpoint = "/air-quality/{day}";

    // Make non-constructable
    private RadarAPIEndpoints() {
    }
}
