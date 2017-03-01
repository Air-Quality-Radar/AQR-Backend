package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarParser {
    private static final String SearchTimestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public String getDateStringFromCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%s,%s,%s", year, day, minutes);
    }

    public Calendar getCalendarFromSearchTimestamp(String searchTimestamp) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(SearchTimestampFormat);
        Date date = formatter.parse(searchTimestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
