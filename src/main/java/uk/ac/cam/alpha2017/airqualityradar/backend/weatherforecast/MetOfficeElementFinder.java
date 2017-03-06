package uk.ac.cam.alpha2017.airqualityradar.backend.weatherforecast;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;

public class MetOfficeElementFinder {

    private boolean sameDay(Calendar date1, Calendar date2){
        // are the dates the same
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }

    public Element findElementForDate(NodeList dayList, Calendar requestDate) throws RecordNotFoundException {
        Element element = null;

        for (int i = 0; i < dayList.getLength(); i++){
            Element e = (Element) dayList.item(i);
            Calendar elementCalendar = DatatypeConverter.parseDate(e.getAttribute("value"));
            if(sameDay(requestDate, elementCalendar)){
                element = e;
                break;
            }
        }

        if (element == null) {
            throw new RecordNotFoundException(requestDate);
        }

        return element;
    }
}
