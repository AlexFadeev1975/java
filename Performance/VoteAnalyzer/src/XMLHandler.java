import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private HashMap<Voter, Integer> voterCounts;
    private TreeMap<String, Date[]> workTimeStations;
    String stationNumber;
    Date visitDate;
    Date[] arrPeriod;

    public XMLHandler() {

        voterCounts = new HashMap<>();
        workTimeStations = new TreeMap<>();
        arrPeriod = new Date[2];

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("voter") & voter == null) {
            try {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (qName.equals("visit") & voter != null) {
            String station = attributes.getValue("station");
            try {
                visitDate = visitDateFormat.parse(attributes.getValue("time"));
                Instant current = visitDate.toInstant();
                LocalDateTime time = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                stationNumber = "Station " + addNullToNumber(Integer.parseInt(station)) + " - " + time.format(formatter);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int count = voterCounts.getOrDefault(voter, 0);
            voterCounts.put(voter, count + 1);

            if (!workTimeStations.containsKey(stationNumber)) {

                arrPeriod = new Date[]{visitDate, visitDate};
                workTimeStations.put(stationNumber, arrPeriod);
            } else {
                Date[] arr = workTimeStations.get(stationNumber);
                if (arr[0].compareTo(visitDate) == -1) {
                    arrPeriod = new Date[]{visitDate, arr[1]};
                    workTimeStations.put(stationNumber, arrPeriod);
                }
                if (arr[1].compareTo(visitDate) == 1) {
                    arrPeriod = new Date[]{arr[0], visitDate};
                    workTimeStations.put(stationNumber, arrPeriod);
                }
            }
        }
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
