package uk.ac.cam.alpha2017.airqualityradar.backend.services;

import org.junit.Before;
import org.junit.Test;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.HistoricalDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.PredictionsDataProvider;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors.AzureTableConnector;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials.StorageConnectionInfo;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials.StorageCredentials;
import uk.ac.cam.alpha2017.airqualityradar.backend.data.dataconverters.DataPointConverter;
import uk.ac.cam.alpha2017.airqualityradar.backend.models.DataPoint;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by henry on 06/03/2017.
 */
public class RadarDataServiceTest {
    private Calendar cal;
    private HistoricalDataProvider hisProvider;
    private PredictionsDataProvider preProvider;
    private StorageConnectionInfo info;

    @Before
    public void setup(){
        StorageCredentials credentials = new StorageCredentials();
        info = credentials.getConnectionInfo();

        AzureTableConnector azure = new AzureTableConnector(info);
        DataPointConverter converter = new DataPointConverter();

        hisProvider = new HistoricalDataProvider(azure, converter);
        preProvider = new PredictionsDataProvider(azure, converter);
        cal = Calendar.getInstance();

    }

    @Test
    public void testCalendarRange(){
        RadarDataService service = new RadarDataService(hisProvider,preProvider);
        List<DataPoint> dataPoints = service.getDataPoints(cal);

        DataPoint first = dataPoints.get(0);
        DataPoint last = dataPoints.get(dataPoints.size() - 1);

        assertEquals(first.getCalendar().get(Calendar.DATE),last.getCalendar().get(Calendar.DATE) - 7);

        Iterator<DataPoint> iterator = dataPoints.iterator();
        DataPoint previous = iterator.next();
        while(iterator.hasNext()){
            DataPoint current = iterator.next();
            Calendar currentCal = current.getCalendar();
            Calendar previousCal = previous.getCalendar();

            assert(previousCal.getTime().getTime() <= currentCal.getTime().getTime());

            previous = current;
        }
    }

    @Test
    public void testInvalidKey(){
        info.setAccountKey("invalidkey");

        RadarDataService service = new RadarDataService(hisProvider,preProvider);

        assertThrows(RuntimeException.class, () -> service.getDataPoints(cal));
    }

    @Test
    public void testInvalidAccount(){
        info.setAccountName("invalidaccountname");

        RadarDataService service = new RadarDataService(hisProvider,preProvider);

        assertThrows(RuntimeException.class, () -> service.getDataPoints(cal));
    }
}
