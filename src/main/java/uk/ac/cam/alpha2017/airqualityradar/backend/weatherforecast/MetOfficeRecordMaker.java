package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.util.Calendar;

/**
 * Creates a weather record object corresponding to a given date/time and location code
 *
 * Created by henry on 13.2.17.
 */

public class MetOfficeRecordMaker {
    private int toNearestMeasure(int requestMin){
        //round requested time to nearest measurement
        int firstMeasure = 360;
        int lastMeasure = 1260;

        if(requestMin < firstMeasure){
            return firstMeasure;
        } else if(requestMin > lastMeasure){
            return lastMeasure;
        } else {
            int quotient = requestMin / 180;
            int remainder = requestMin % 180;

            if(remainder < 90){
                return quotient * 180;
            } else {
                return (quotient * 180) + 180;
            }
        }
    }

    public MetOfficeRecord createRecord(Calendar requestDate, String location)
            throws RecordNotFoundException, ParserConfigurationException, SAXException, IOException {

        // Get nearest forecast to the time specified
        int requestMin = 60*requestDate.get(Calendar.HOUR_OF_DAY) + requestDate.get(Calendar.MINUTE);
        int recordTime = toNearestMeasure(requestMin);


        // Access XML resource
        URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/xml/"+location+"?res=3hourly&key=5517a045-d7d4-4866-9fce-8e28fefac593");	//causes IOException
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();	//causes ParserConfigurationException
        Document doc = builder.parse(url.openStream());	//causes SAXException, IOException

        // Find the requested day
        NodeList dayList = doc.getElementsByTagName("Period");
        MetOfficeElementFinder dayFinder = new MetOfficeElementFinder();
        Element eDay = dayFinder.findElementForDate(dayList, requestDate);


        // Find measure within the day
        int measureNo = 0;
        NodeList recordingList = eDay.getElementsByTagName("Rep");
        for(int i=0; i<recordingList.getLength(); i++){
            if(recordingList.item(i).getTextContent().equals(Integer.toString(recordTime))){
                measureNo = i;
            }
        }
        Node recording = recordingList.item(measureNo);
        Element eRecording = (Element) recording;


        // Get all the attributes
        String windDirection = eRecording.getAttribute("D");
        float windGust = Float.parseFloat(eRecording.getAttribute("G"));
        float humidity = Float.parseFloat(eRecording.getAttribute("H"));
        float precipitationProb = Float.parseFloat(eRecording.getAttribute("Pp"));
        float windSpeed = Float.parseFloat(eRecording.getAttribute("S"));
        float temperature = Float.parseFloat(eRecording.getAttribute("T"));
        String visibility = eRecording.getAttribute("V");
        int sigWeatherCode = Integer.parseInt(eRecording.getAttribute("W"));

        Calendar recordCalendar = DatatypeConverter.parseDate(eDay.getAttribute("value"));
        int minPastMidnight = Integer.parseInt(eRecording.getTextContent());
        recordCalendar.add(Calendar.MINUTE, minPastMidnight);


        // Create the record
        MetOfficeRecord record = new MetOfficeRecord(recordCalendar, sigWeatherCode, visibility,
                temperature, windSpeed, precipitationProb, humidity, windGust, windDirection);

        return record;
    }

}
