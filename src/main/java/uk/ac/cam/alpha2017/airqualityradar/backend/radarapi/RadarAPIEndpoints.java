package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

public interface RadarAPIEndpoints {
    // Stores the string values of the endpoints of the AQR API.

    String HelloWorldEndpoint = "/hello-world";
    String AirQualityEndpoint = "/air-quality/{day}";
}
