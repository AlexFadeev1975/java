import java.util.*;

public class CoolNumbers {

    public static List<String> generateCoolNumbers() {
        List<String> coolNumber = new ArrayList<>();
        List<String> letter = List.of("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х");
        final int MAX_REGION_NUMBER = 199;
        final int MIN_REGION_NUMBER = 1;
        String number;

        for (int i = 0; i <= 2000000; i++) {

            String letter1 = letter.get((int) (Math.random() * (letter.size() - 1))); // первая буква

            int digit2 = (int) (Math.random() * 10);                                 // вторая цифра

            String letter5 = letter.get((int) (Math.random() * (letter.size() - 1))); // пятая буква

            String letter6 = letter.get((int) (Math.random() * (letter.size() - 1)));  // шестая буква

            int randomRegion = (int) (Math.random() * ((MAX_REGION_NUMBER + 1 - MIN_REGION_NUMBER) + MIN_REGION_NUMBER));
            String region = (randomRegion < 10) ? String.format("%02d", randomRegion) : Integer.toString(randomRegion); // регион

            number = letter1 + digit2 + digit2 + digit2 + letter5 + letter6 + region;

            coolNumber.add(number);

        }
        return coolNumber;
    }

    public static boolean bruteForceSearchInList(List<String> list, String number) {
        boolean result = false;

        for (String item : list) {
            if (item.equals(number)) {

                result = true;
            }
        }
        return result;
    }

    public static boolean binarySearchInList(List<String> sortedList, String number) {

        Collections.sort(sortedList);
        int item = Collections.binarySearch(sortedList, number);

        return (item >= 0);
    }

    public static boolean searchInHashSet(HashSet<String> hashSet, String number) {

        return hashSet.contains(number);
    }

    public static boolean searchInTreeSet(TreeSet<String> treeSet, String number) {

        return treeSet.contains(number);
    }

    public static void print(Boolean resultSearch, long start, long finish, String varianSearch) {
        long time = finish - start;
        String result = (resultSearch) ? "номер найден, " : "номер не найден, ";
        System.out.println(varianSearch + result + "поиск занял: " + time + "нс");
    }
}
