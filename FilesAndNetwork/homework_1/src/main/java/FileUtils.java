import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtils<i> {


    public static long calculateFolderSize(String path) throws Exception {

        try (Stream<Path> stream = Files.walk(Paths.get(path), 4)) {
            return stream.filter(Files::isRegularFile).map(x -> x.toFile().length()).mapToLong(Long::valueOf).sum();
        }
    }

    public static void toString(String filename, long filesSize) {
        String[] attr = {"B", "Kb", "Mb", "Gb"};
        for (int i = 0; i < 4; i++) {
            if (filesSize < 1024) {
                System.out.println("Размер папки " + filename + " " + filesSize + " " + attr[i]);
                break;
            } else {
                filesSize = (long) (filesSize / 1024);

            }
        }
    }
}
