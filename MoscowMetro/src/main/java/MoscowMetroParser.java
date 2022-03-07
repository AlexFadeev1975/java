import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MoscowMetroParser {

    public void parserHtml(String url) throws IOException {
        JSONObject station = new JSONObject();
        JSONArray listStations = new JSONArray();
        JSONObject numberLineForStation = new JSONObject();
        JSONArray line = new JSONArray();
        JSONArray connections = new JSONArray();
        Map<String, String> lineNumberForConnections = new HashMap<>();

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Elements elements = doc.select("span");

        elements.forEach(x -> {
            String numberLine = x.select("span[data-line]").attr("data-line");
            String nameLine = x.select("span[data-line]").text();
            if (!numberLine.isEmpty()) {
                lineNumberForConnections.put(nameLine, numberLine);
            }
        });
        elements.forEach(x -> {
            String numberLine = x.select("span[data-line]").attr("data-line");
            String nameLine = x.select("span[data-line]").text();
            String nameStation = x.select("span.name").text();
            String nameConnection = x.select("span[title]").attr("title");

            if ((!numberLine.isEmpty()) && (!numberLine.equals("1"))) {
                JSONArray listStationsToPut = new JSONArray();
                listStationsToPut.addAll(listStations);
                station.put(numberLineForStation.get("number"), listStationsToPut);
                listStations.clear();
            }
            if (!numberLine.isEmpty()) {
                JSONObject lineNumber = new JSONObject();
                numberLineForStation.put("number", numberLine);
                lineNumber.put("number", numberLine);
                lineNumber.put("name", nameLine);
                line.add(lineNumber);
            }
            if (!nameStation.isEmpty()) {
                listStations.add(nameStation);

            }
            if (!nameConnection.isEmpty()) {
                JSONObject stationConnect1 = new JSONObject();
                stationConnect1.put("Line", numberLineForStation.get("number"));
                stationConnect1.put("station", listStations.get(listStations.size() - 1));
                String[] parseLine = nameConnection.split(" ", 4);
                String[] parseLastPartLine = parseLine[3].split("»");
                String stationConnect = nameConnection.substring((nameConnection.indexOf("«") + 1), nameConnection.lastIndexOf("»"));
                String tempLines = parseLastPartLine[1].trim();
                if (tempLines.equals("МЦК")) {
                    tempLines = "Центральное кольцо";
                }
                if (tempLines.equals("Курско-Рижского (второго) диаметра")) {
                    tempLines = "МЦД-2";
                }
                if (tempLines.equals("Белорусско-Савеловского (первого) диаметра")) {
                    tempLines = "МЦД-1";
                }
                for (String item : lineNumberForConnections.keySet()) {
                    if (item.substring(0, 3).matches(tempLines.substring(0, 3))) {
                        tempLines = lineNumberForConnections.get(item);
                        break;
                    }
                }
                JSONObject stationConnect2 = new JSONObject();
                stationConnect2.put("Line", tempLines);
                stationConnect2.put("station", stationConnect);
                JSONArray twoStationConnect = new JSONArray();
                twoStationConnect.add(stationConnect1);
                twoStationConnect.add(stationConnect2);
                connections.add(twoStationConnect);
            }
        });

        JSONObject allDate = new JSONObject();
        allDate.put("stations", station);
        allDate.put("lines", line);
        allDate.put("connections", connections);
        try {
            FileWriter file = new FileWriter("src/main/resources/map.json");
            file.write(allDate.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
