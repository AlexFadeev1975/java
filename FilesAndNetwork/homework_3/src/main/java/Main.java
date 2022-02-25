import java.io.IOException;

public class Main {
    public static final String file = "C:\\movementList.csv";

    public static void main(String[] args) {

        Movements movements = new Movements(file);
        movements.print();

    }
}
