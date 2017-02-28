package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the functionality of MetOfficeRecordTest
 *
 * Created by henry on 14.2.17.
 */

public class RecordMakerTest {

    public static List<Map.Entry<Integer, Integer>> times(){
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(540, 540),
                new AbstractMap.SimpleEntry<>(600, 540),
                new AbstractMap.SimpleEntry<>(700, 720),
                new AbstractMap.SimpleEntry<>(100, 180),
                new AbstractMap.SimpleEntry<>(1400, 1260),
                new AbstractMap.SimpleEntry<>(990, 1080)
        );

    }

    private MetOfficeRecordMaker testMaker;
    private Calendar testCal;

    @BeforeEach
    public void setup(){
        testMaker = new MetOfficeRecordMaker();
        testCal = Calendar.getInstance();
        testCal.add(Calendar.DATE,1);

    }

    @TestFactory
    Stream<DynamicTest> testRoundingConditions() {
        List<Map.Entry<Integer,Integer>> testData = times();
        return testData.stream()
                .map(datum -> DynamicTest.dynamicTest(
                        "Testing " + datum,
                        () -> RoundingTest(
                                datum.getKey(), datum.getValue())));
    }

    public void RoundingTest(int minPastMidnight, int expectedMinutes) throws ParserConfigurationException, SAXException, RecordNotFoundException, IOException {
        testCal.set(Calendar.HOUR_OF_DAY, minPastMidnight/60);
        testCal.set(Calendar.MINUTE, minPastMidnight%60);

        MetOfficeRecord testRecord = testMaker.createRecord(testCal,"99123");
        Calendar recordCal = testRecord.getCalendar();
        int recordValue = recordCal.get(Calendar.HOUR_OF_DAY) * 60;

        assertEquals(recordValue, expectedMinutes);

    }
}
