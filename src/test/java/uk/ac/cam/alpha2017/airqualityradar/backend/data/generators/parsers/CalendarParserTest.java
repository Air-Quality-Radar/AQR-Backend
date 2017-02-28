package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalendarParserTest {

    CalendarParser classToTest;

    @BeforeEach
    private void setup () {
        classToTest = new CalendarParser();
    }

    @Test
    public void getDateStringFromCalendarReturnsRightDateString() {
        final String result = "2000,24,600";
        Calendar mockedCalendar = Mockito.mock(Calendar.class);
        when(mockedCalendar.get(Calendar.YEAR)).thenReturn(2000);
        when(mockedCalendar.get(Calendar.DAY_OF_YEAR)).thenReturn(24);
        when(mockedCalendar.get(Calendar.MINUTE)).thenReturn(600);


        assertEquals(classToTest.getDateStringFromCalendar(mockedCalendar),result);
    }

    @Test
    public void getCalendarFromDateStringReturnsRightCalendar() {

    }
}
