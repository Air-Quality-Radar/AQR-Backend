package uk.ac.cam.alpha2017.airqualityradar.backend.data.generators.parsers;

import uk.ac.cam.alpha2017.airqualityradar.backend.models.Location;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocationParser {
    public String getLocationStringFromLocation(Location location) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

        String longitude = String.format("%s", df.format(location.getLongitude()));
        String latitude = String.format("%s", df.format(location.getLatitude()));
        return String.format("%s,%s", latitude, longitude);
    }
}
