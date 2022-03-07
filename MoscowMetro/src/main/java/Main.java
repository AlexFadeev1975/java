import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {


        MoscowMetroParser parser = new MoscowMetroParser();
        JSONObject stationsObject = new JSONObject();
        JSONArray connectionsArray = new JSONArray();
        parser.parserHtml("https://skillbox-java.github.io");

        try {
            JSONParser parserFile = new JSONParser();
            JSONObject jsonData = (JSONObject) parserFile.parse(getJsonFile());

            stationsObject = (JSONObject) jsonData.get("stations");

            connectionsArray = (JSONArray) jsonData.get("connections");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Set<String> keys = stationsObject.keySet();
        for (String item : keys) {
            JSONArray arr = (JSONArray) stationsObject.get(item);
            System.out.println("Линия  " + item + "  Количество станций  " + arr.size());
        }
        System.out.println("Количество переходов:" + connectionsArray.size());

    }

    private static String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/map.json"));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
