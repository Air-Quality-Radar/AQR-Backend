package uk.ac.cam.alpha2017.airqualityradar.backend.data;

// Include the following imports to use table APIs

import com.microsoft.azure.storage.StorageException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.TableDoesNotExistException;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.CalendarConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Testing {
    public static Calendar toCalendar(String dateString) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static void main(String[] args) throws ParseException, StorageException, InvalidKeyException, URISyntaxException, TableDoesNotExistException {
        HistoricalDataProvider hdp = new HistoricalDataProvider(new AzureTableConnector());

        Calendar fromCalendar = toCalendar("2017-01-09 16:00:00");
        Calendar toCalendar = toCalendar("2017-03-10 16:00:00");

        for (DataPoint x : hdp.getDataPoints(fromCalendar, toCalendar)) {
            System.out.printf("hi %s\n", CalendarConverter.getSearchTimestampForCalendar(x.getCalendar()));
        }
    }
}
