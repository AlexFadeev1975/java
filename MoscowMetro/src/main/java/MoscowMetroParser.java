import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class MoscowMetroParser {
    Set<String> stationCount = new TreeSet();
    Integer connectionCount = 0;

    public void parserHtml(String url) throws IOException {
        JSONObject station = new JSONObject();
        JSONArray listStations = new JSONArray();
        JSONObject lineNumber = new JSONObject();
        JSONObject lineName = new JSONObject();
        JSONArray line = new JSONArray();
        JSONArray listLines = new JSONArray();
        JSONArray connections = new JSONArray();
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Elements elements = doc.select("span");
        elements.forEach(x -> {
            String numberLine = x.select("span[data-line]").attr("data-line");
            String nameLine = x.select("span[data-line]").text();
            String nameStation = x.select("span.name").text();
            String nameConnection = x.select("span[title]").attr("title");
            if ((!numberLine.isEmpty()) && (!numberLine.equals("1"))) {
                station.put(lineNumber.get("number"), listStations);
                stationCount.add("Линия № " + lineNumber.get("number") + "    Количество станций " + listStations.size());
                listStations.clear();
            }
            if (!numberLine.isEmpty()) {
                lineNumber.put("number", numberLine);
                lineName.put("name", nameLine);
                line.add(lineNumber);
                line.add(lineName);
                listLines.add(line);
                line.clear();
            }
            if (!nameStation.isEmpty()) {
                listStations.add(nameStation);
            }
            if (!nameConnection.isEmpty()) {
                connections.add(nameStation + "  " + nameConnection);
            }
        });
        JSONObject allStaions = new JSONObject();
        allStaions.put("stations", station);
        JSONObject allLines = new JSONObject();
        allLines.put("lines", listLines);
        JSONObject allConnections = new JSONObject();
        allConnections.put("connections", connections);
        connectionCount = connections.size();
        JSONArray toWrite = new JSONArray();
        toWrite.add(allStaions);
        toWrite.add(allLines);
        toWrite.add(allConnections);
        try {
            FileWriter file = new FileWriter("map.json");
            file.write(toWrite.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void print() {
        stationCount.forEach(System.out::println);
        System.out.println("Количество переходов " + connectionCount);
    }
}
