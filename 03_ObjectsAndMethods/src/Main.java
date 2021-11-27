public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40, 2, 0.5);
        basket.add("Bread", 30, 3, 1);
        basket.print("Корзина1");

        Basket basket1 = new Basket();
        basket1.add("Milk", 40, 3, 0.5);
        basket1.add("Bread", 30, 2, 1);
        basket1.print("Корзина2");

        Basket basket2 = new Basket();
        basket2.add("Salo", 400, 1, 0.5);
        basket2.add("Pelmeny", 300, 2, 1);
        basket2.print("Корзина3");

    }
}
