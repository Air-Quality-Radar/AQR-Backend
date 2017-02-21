package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import java.util.Calendar;

public class RecordNotFoundException extends Exception {
    public Calendar requestDate;

    public RecordNotFoundException(Calendar requestDate) {
        this.requestDate = requestDate;
    }
}
