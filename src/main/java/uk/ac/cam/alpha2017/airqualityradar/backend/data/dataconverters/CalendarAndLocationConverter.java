package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;

import java.util.Calendar;

/**
 * Created by jirka on 25.2.17.
 */
public class CalendarAndLocationConverter {

    public static String getLocationStringFromLocation(Location location) {
        String longitude = Double.toString(location.getLongitude());
        String latitude = Double.toString(location.getLatitude());

        return String.format("%s,%s",latitude, longitude);
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getNumDaysSinceStartOfYear(Calendar calendar) {
        // One-based, i.e. Jan 1st is day no 1
        int dayNo = calendar.get(Calendar.DAY_OF_YEAR);

        // Subtract 1 to make zero-based
        return dayNo - 1;
    }

    public static int getNumMinutesSinceMidnight(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    public static String getDateStringFromCalendar(Calendar calendar) {
        int year = getYear(calendar);
        int day = getNumDaysSinceStartOfYear(calendar);
        int minutes = getNumMinutesSinceMidnight(calendar);

        return String.format("%d,%d,%d", year, day, minutes);
    }

    public String generateRowKey(Calendar calendar, Location location) {
        return getDateStringFromCalendar(calendar) + "," + getLocationStringFromLocation(location);
    }
}
