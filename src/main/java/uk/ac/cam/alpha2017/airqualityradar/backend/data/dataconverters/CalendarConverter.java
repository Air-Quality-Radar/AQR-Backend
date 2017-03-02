package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarConverter {
    private static final String SearchTimestampFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String getSearchTimestampForCalendar(Calendar calendar) {
        DateFormat formatter = new SimpleDateFormat(SearchTimestampFormat);
        return formatter.format(calendar.getTime());
    }

    public static Calendar getCalendarFromSearchTimestamp(String searchTimestamp) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(SearchTimestampFormat);
        Date date = formatter.parse(searchTimestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
