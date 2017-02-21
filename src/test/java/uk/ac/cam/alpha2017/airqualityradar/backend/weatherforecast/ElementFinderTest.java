package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality of MetOfficeElementFinder
 *
 * Created by henry on 20.2.17.
 */

public class ElementFinderTest {
    private MetOfficeElementFinder testFinder;
    private File testFile;
    private DocumentBuilderFactory testFactory;
    private DocumentBuilder testBuilder;
    private Document testDoc;
    private NodeList testDayList;

    @BeforeEach
    public void setup() throws IOException, SAXException, ParserConfigurationException {
        testFinder = new MetOfficeElementFinder();
        testFile = new File("src/test/java/uk/ac/cam/alpha2017/airqualityradar/backend/weatherforecast/testXMLForecast.xml");
        testFactory = DocumentBuilderFactory.newInstance();
        testBuilder = testFactory.newDocumentBuilder();
        testDoc = testBuilder.parse(testFile);

        testDayList = testDoc.getElementsByTagName("Period");

    }

    @Test
    public void testFindElementValid() throws ParserConfigurationException, IOException, SAXException, RecordNotFoundException {
        //test the .findElementForDate function for valid date inputs
        Calendar testDate = Calendar.getInstance();
        testDate.set(2017, 1, 16);

        Element found = testFinder.findElementForDate(testDayList, testDate);

        assertEquals(found.getAttribute("value"), "2017-02-16Z");

    }

    @Test
    public void testFindElementInvalid() throws IOException, SAXException, ParserConfigurationException {
        //test the .findElementForDate function for invalid date inputs
        Calendar invalidDate = Calendar.getInstance();
        invalidDate.set(2017, 1, 21);


        assertThrows(RecordNotFoundException.class, () -> {
            testFinder.findElementForDate(testDayList, invalidDate);
        });
    }
}