import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите путь до папки: ");
            String nextLine = scanner.nextLine();
            try {
                FileUtils.toString(nextLine, FileUtils.calculateFolderSize(nextLine));

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
