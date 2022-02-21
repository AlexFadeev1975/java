import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileUtils {
    public static void copyFolder(String sourceDirectory, String destinationDirectory) throws IOException {

        Path sourceDir = Paths.get(sourceDirectory);
        Path destinationDir = Paths.get(destinationDirectory);
        Stream<Path> stream = Files.walk(Paths.get(sourceDirectory));
        stream.forEach(x -> {
            if (x.toFile().isDirectory()) {
                try {
                    Files.createDirectory(destinationDir.resolve(sourceDir.relativize(x)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Files.copy(x, destinationDir.resolve(sourceDir.relativize(x)), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
