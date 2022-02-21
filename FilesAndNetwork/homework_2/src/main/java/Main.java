import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите путь папки для копирования: ");
            String sourceDirectory = scanner.nextLine();
            System.out.println("Введите путь папки назначения: ");
            String destinationDirectory = scanner.nextLine();

            Path sourceDir = Paths.get(sourceDirectory);
            if (sourceDir.toFile().exists()) {
                try {
                    FileUtils.copyFolder(sourceDirectory, destinationDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Такой папки не существует!");
            }


        }
    }
}
