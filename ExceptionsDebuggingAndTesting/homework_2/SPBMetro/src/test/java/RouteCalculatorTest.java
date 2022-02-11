import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> route2, route1, route0;
    public StationIndex stationIndex = new StationIndex();

    @Override
    protected void setUp() throws Exception {
        Line line3 = new Line(3, "Три");
        Line line4 = new Line(4, "Четыре");
        Line line5 = new Line(5, "Пять");
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);
        stationIndex.addLine(line5);

        Station station13 = new Station("Ломоносовская", line3);
        Station station23 = new Station("Елизаровская", line3);
        Station station33 = new Station("Площадь Александра Невского1", line3);
        Station station14 = new Station("Площадь Александра Невского2", line4);
        Station station24 = new Station("Лиговский проспект", line4);
        Station station34 = new Station("Достоевская", line4);
        Station station44 = new Station("Спасская", line4);
        Station station15 = new Station("Садовая", line5);
        Station station25 = new Station("Спортивная", line5);
        Station station35 = new Station("Чкаловская", line5);
        stationIndex.addStation(station13);
        line3.addStation(station13);
        stationIndex.addStation(station23);
        line3.addStation(station23);
        stationIndex.addStation(station33);
        line3.addStation(station33);
        stationIndex.addStation(station14);
        line4.addStation(station14);
        stationIndex.addStation(station24);
        line4.addStation(station24);
        stationIndex.addStation(station34);
        line4.addStation(station34);
        stationIndex.addStation(station44);
        line4.addStation(station44);
        stationIndex.addStation(station15);
        line5.addStation(station15);
        stationIndex.addStation(station25);
        line5.addStation(station25);
        stationIndex.addStation(station35);
        line5.addStation(station35);

        List<Station> connectionStations = new ArrayList<>();
        connectionStations.add(station33);
        connectionStations.add(station14);
        stationIndex.addConnection(connectionStations);
        connectionStations.clear();
        connectionStations.add(station44);
        connectionStations.add(station15);
        stationIndex.addConnection(connectionStations);

        route2 = new ArrayList<>();
        route2.add(station13);
        route2.add(station23);
        route2.add(station33);
        route2.add(station14);
        route2.add(station24);
        route2.add(station34);
        route2.add(station44);
        route2.add(station15);
        route2.add(station25);
        route2.add(station35);

        route1 = new ArrayList<>();
        route1.add(station13);
        route1.add(station23);
        route1.add(station33);
        route1.add(station14);
        route1.add(station24);
        route1.add(station34);

        route0 = new ArrayList<>();
        route0.add(station14);
        route0.add(station24);
        route0.add(station34);
        route0.add(station44);

    }

    public void testCalculateDuration() {

        double actual = RouteCalculator.calculateDuration(route2);
        double expected = 24.5;

        assertEquals(expected, actual);

    }

    public void testGetShortestRoute() {

        // с двумя пересадками
        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> actual2 = routeCalculator.getShortestRoute(stationIndex.getStation("Ломоносовская", 3), stationIndex.getStation("Чкаловская", 5));
        List<Station> expected2 = route2;

        assertEquals(expected2, actual2);
        // c одной пересадкой
        List<Station> actual1 = routeCalculator.getShortestRoute(stationIndex.getStation("Ломоносовская", 3), stationIndex.getStation("Достоевская", 4));
        List<Station> expected1 = route1;

        assertEquals(expected1, actual1);

        // без пересадок
        List<Station> actual0 = routeCalculator.getShortestRoute(stationIndex.getStation("Площадь Александра Невского2", 4), stationIndex.getStation("Спасская", 4));
        List<Station> expected0 = route0;

        assertEquals(expected0, actual0);
    }

    public void testToString() {

        String lineName = route2.get(0).getLine().getName();
        String stationName = route2.get(1).toString();

        assertEquals("Три Елизаровская", lineName + " " + stationName);

    }
}