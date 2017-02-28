package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators;

import uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers.CalendarParser;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers.LocationParser;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;

import java.util.Calendar;

public class DataRowEntityRowKeyGenerator {
    LocationParser lp;
    CalendarParser cp;

    public DataRowEntityRowKeyGenerator(){
        lp = new LocationParser();
        cp = new CalendarParser();
    }

    public String generateRowKey(Calendar calendar, Location location) {
        return cp.getDateStringFromCalendar(calendar) + "," + lp.getLocationStringFromLocation(location);
    }
}
