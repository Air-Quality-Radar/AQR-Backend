package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;

import java.util.Calendar;

/**
 * Created by jirka on 25.2.17.
 */
public class DataRowEntitityRowKeyGenerator {

    private static String getLocationStringFromLocation(Location location) {
        String longitude = Double.toString(location.getLongitude());
        String latitude = Double.toString(location.getLatitude());
        return String.format("%s,%s",latitude, longitude);
    }

    private static String getDateStringFromCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%d,%d,%d",year,day,minutes);
    }

    public static String generateRowKey(Calendar calendar, Location location) {
        return getDateStringFromCalendar(calendar) + "," + getLocationStringFromLocation(location);
    }
}
