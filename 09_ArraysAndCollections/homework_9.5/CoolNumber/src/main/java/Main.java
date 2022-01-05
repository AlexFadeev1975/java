import java.util.*;

public class Main {
    /*
    TODO:
     - реализовать методы класса CoolNumbers
     - посчитать время поиска введимого номера в консоль в каждой из структуры данных
     - проанализоровать полученные данные
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Введите номер: ");
            String number = scanner.nextLine();

            List<String> list = CoolNumbers.generateCoolNumbers();

            String variantSearch = "Поиск перебором: ";
            long start = System.nanoTime();
            boolean resultSearch = CoolNumbers.bruteForceSearchInList(list, number);
            long finish = System.nanoTime();
            CoolNumbers.print(resultSearch, start, finish, variantSearch);


            List<String> sortedList = list;
            Collections.sort(sortedList);
            variantSearch = "Бинарный поиск: ";
            start = System.nanoTime();
            resultSearch = CoolNumbers.binarySearchInList(list, number);
            finish = System.nanoTime();
            CoolNumbers.print(resultSearch, start, finish, variantSearch);

            HashSet<String> hashSet = new HashSet<>(list);
            variantSearch = "Поиск в HashSet: ";
            start = System.nanoTime();
            resultSearch = CoolNumbers.searchInHashSet(hashSet, number);
            finish = System.nanoTime();
            CoolNumbers.print(resultSearch, start, finish, variantSearch);

            TreeSet<String> treeSet = new TreeSet<>(list);
            variantSearch = "Поиск в TreeSet: ";
            start = System.nanoTime();
            resultSearch = CoolNumbers.searchInTreeSet(treeSet, number);
            finish = System.nanoTime();
            CoolNumbers.print(resultSearch, start, finish, variantSearch);

        }
    }
}
