package uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalendarConverterTest {
    private CalendarConverter converter;

    @Before
    public void setup() {
        converter = new CalendarConverter();
    }

    @Test
    public void testGetTimeStamp() {
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(Calendar.YEAR, 2030);
        testCalendar.set(Calendar.MONTH, 11);
        testCalendar.set(Calendar.DATE, 9);
        testCalendar.set(Calendar.HOUR_OF_DAY, 6);
        testCalendar.set(Calendar.MINUTE, 4);
        testCalendar.set(Calendar.SECOND, 8);

        String timeStamp = converter.getSearchTimestampForCalendar(testCalendar);
        assertEquals(timeStamp, "2030-12-09T06:04:08Z");

        testCalendar.set(Calendar.SECOND, 61);

        timeStamp = converter.getSearchTimestampForCalendar(testCalendar);
        assertEquals(timeStamp, "2030-12-09T06:05:01Z");

        testCalendar.set(Calendar.MONTH, 13);
        timeStamp = converter.getSearchTimestampForCalendar(testCalendar);
        assertEquals(timeStamp, "2031-02-09T06:05:01Z");

    }

    @Test
    public void testGetCalendar() throws ParseException {
        String testTimeStamp = "2031-02-09T06:05:01Z";

        Calendar generatedCal = converter.getCalendarFromSearchTimestamp(testTimeStamp);

        assertEquals(generatedCal.get(Calendar.YEAR), 2031);
        assertEquals(generatedCal.get(Calendar.MONTH), 1);
        assertEquals(generatedCal.get(Calendar.DATE), 9);
        assertEquals(generatedCal.get(Calendar.HOUR_OF_DAY), 6);
        assertEquals(generatedCal.get(Calendar.MINUTE), 5);
        assertEquals(generatedCal.get(Calendar.SECOND), 1);

    }

    @Test
    public void testInvalidCalendar() {
        String testTimeStamp = "2031-00-01-00:00:00Z";

        assertThrows(ParseException.class, () -> converter.getCalendarFromSearchTimestamp(testTimeStamp));
    }
}
