package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.util.Calendar;
import java.util.List;

public interface DataService {
    List<DataPoint> getDataPoints(Calendar calendar);
}
