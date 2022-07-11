import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class XMLHandler extends DefaultHandler {

    private String voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private HashMap<Voter, Integer> voterCounts;
    private TreeMap<String, Date[]> workTimeStations;
    String station;
    String birthDay;
    String time;
    StringBuilder stringQuery = new StringBuilder();
    int valuesCounter = 1;
    static int LIMIT = 500000;

    public XMLHandler() {

        voterCounts = new HashMap<>();
        workTimeStations = new TreeMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("voter") & voter == null) {
            birthDay = attributes.getValue("birthDay");
            voter = attributes.getValue("name");

        } else if (qName.equals("visit") & voter != null) {
            station = attributes.getValue("station");
            time = attributes.getValue("time");

            stringQuery.append(((stringQuery.length() < 1) ? "('" : ",('") + voter + "','" + birthDay + "','" + station + "','" + time + "')");
            valuesCounter = valuesCounter + 1;

            if (valuesCounter > LIMIT) {

                DBConnection.sqlFileLoader(stringQuery.toString());
                stringQuery.setLength(0);
                valuesCounter = 1;

            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        DBConnection.sqlFileLoader(stringQuery.toString());

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("voter")) {
            voter = null;
        }
    }

    public void printDuplicatedVoters() {
        for (Voter v : voterCounts.keySet()) {
            int count = voterCounts.get(v);
            if (count > 1) {
                System.out.println(v.toString() + " - " + count);
            }
        }
    }

    private static String addNullToNumber(int number) {
        String numberStr = Integer.toString(number);
        int padSize = 3 - numberStr.length();
        if (padSize != 0) {
            numberStr = (padSize == 2) ? "00" + numberStr : "0" + numberStr;
        }

        return numberStr;
    }

    public void printStationWorkTime() {
        for (String s : workTimeStations.keySet()) {
            Date[] arr = workTimeStations.get(s);
            long period = arr[1].getTime() - arr[0].getTime();
            Timestamp stp = new Timestamp(period);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            System.out.println(s + " work time: " + formatter.format(stp));
        }
    }
}
