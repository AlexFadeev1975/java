public class Basket {

    private static int count = 0;
    private String items = "";
    private static int totalPrice = 0;
    private int limit;
    private double totalWeight = 0;
    public static int totalPriceAllItems = 0;
    public static int totalCountAllItems = 0;
    public static double averagePrice;
    public static int countBaskets = 0;
    public static double avePriceBasket;

    public Basket() {
        increaseCount(1);
        items = "Список товаров:";
        this.limit = 1000000;
        totalCountBasket();
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
        totalCountBasket();

    }

    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        Basket.totalPrice = totalPrice;
        totalCountBasket();
    }

    public static int getCount() {
        return count;
    }

    public static void increaseCount(int count) {
        Basket.count = Basket.count + count;
    }

    public static void totalCountBasket() {
        int countBasket = 1;
        Basket.countBaskets = Basket.countBaskets + countBasket;
    }

    public static void allPriceCountItems() {
        totalPriceAllItems = totalPriceAllItems + totalPrice;
        totalCountAllItems = totalCountAllItems + count;
    }

    public static void averagePriceItem() {
        averagePrice = (double) totalPriceAllItems / (double) totalCountAllItems;
    }

    public static void averagePriceBasket() {
        avePriceBasket = (double) totalPriceAllItems / (double) countBaskets;

    }

    public void add(String name, int price) {
        add(name, price, 1, 0);
    }

    public void add(String name, int price, int count) {
        add(name, price, count, 0);
    }

    public void add(String name, int price, int count, double weight) {

        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occured :(");
            return;
        }

        items = items + "\n" + name + " - " +
                count + " шт. - " + price + " руб" + "  Вес за 1 шт: " + weight + "кг";
        totalPrice = totalPrice + count * price;
        totalWeight = totalWeight + count * weight;
        allPriceCountItems();
        averagePriceItem();
        averagePriceBasket();
    }

    public void clear() {
        items = "";
        totalPrice = 0;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println(items);
            System.out.println("Общая стоимость всех товаров: " + totalPriceAllItems + " руб"
                    + "\n" + "Общая количество всех товаров: " + totalCountAllItems + " шт");
            System.out.println("Средняя цена товара: " + averagePrice + " руб");
            System.out.println("Средняя стоимость корзины: " + avePriceBasket + " руб");

        }
    }

}
