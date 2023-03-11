

import javax.swing.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toMap;


public class Main {
    public static void main(String[] args) {


            List<Integer> delList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                delList.add(i);
            }

        int firstIndex = 0;
            //*** change this code which raises ConcurrentModificationException
            for (Integer num : delList) {
                if (num == 10) { firstIndex = delList.indexOf(num);

                break;};
            }
            //***
            ;

            System.out.println(delList.subList(firstIndex, 20));
    }
}