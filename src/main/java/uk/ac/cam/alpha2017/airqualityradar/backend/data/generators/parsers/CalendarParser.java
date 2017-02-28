package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers;

import java.util.Calendar;

public class CalendarParser {
    public String getDateStringFromCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%s,%s,%s", year, day, minutes);
    }
}
