import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String srcFolder = "D://src";
        String dstFolder = "D://dst";
        int intWight = 800;

        int core = Runtime.getRuntime().availableProcessors();
        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();
        int nameFilesArray = 0;
        int size;
        Map<Integer, File[]> array = new HashMap<>();
        assert files != null;
        if (files.length > core) {
            size = files.length / (core - 1);
        } else {
            size = 1;
        }
        for (int i = 0; i < files.length; i = i + size) {
            if (i >= files.length / (core - 1) * (core - 1)) {
                i = files.length - 1;
                size = files.length - size * (core - 1);
            }
            File[] filesArray = new File[size];
            System.arraycopy(files, i, filesArray, 0, size);
            array.put(nameFilesArray, filesArray);
            nameFilesArray++;
        }
        for (int i = 0; i < array.size(); i++) {
            new ImageResizer(array.get(i), dstFolder, intWight).run();
        }
    }
}