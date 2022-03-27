import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer extends Thread {

    private File[] files;
    private String dstFolder;
    private int intWight;

    public ImageResizer(File[] files, String dstFolder, int intWight) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.intWight = intWight;
    }

    @Override
    public void run() {
        for (File file : files) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image == null) {
                continue;
            }
            int newHeight = (int) Math.round(
                    image.getHeight() / (image.getWidth() / (double) intWight));
            BufferedImage newImage = Scalr.resize(image, intWight, newHeight);
            File newFile = new File(dstFolder + "/" + file.getName());
            int lastIndexOf = file.getName().lastIndexOf(".");
            String extension = file.getName().substring(lastIndexOf + 1);
            try {
                ImageIO.write(newImage, extension, newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

