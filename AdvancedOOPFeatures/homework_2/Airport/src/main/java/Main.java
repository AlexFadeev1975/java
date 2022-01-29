import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println(Airport.getInstance());

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        //TODO Метод должден вернуть список рейсов вылетающих в ближайшие два часа.
        long currentTime = System.currentTimeMillis();
        long timePlusTwoHours = currentTime + 7200000l;
        Date timeNow = new Date(currentTime);
        Date timePlus = new Date(timePlusTwoHours);
        List<Flight> flight = new ArrayList<>();

        for (Terminal item : airport.getTerminals()) {

            flight.addAll(item.getFlights());
        }

        return flight.stream()
                .filter(i -> i.getType() == Flight.Type.DEPARTURE)
                .filter(x -> x.getDate().before(timePlus))
                .filter(y -> y.getDate().after(timeNow))
                .collect(Collectors.toList());

    }
}