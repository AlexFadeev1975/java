import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageFinder {

    public void parserHtml(String url, String attrPage, String attrLine) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(attrPage);
        elements.forEach(x -> {
            String attr = x.attr(attrLine);
            try {
                URL fileUrl = new URL(attr);
                File file = new File(attr);
                String name = file.getName();
                InputStream i = fileUrl.openStream();
                Files.copy(i, Path.of("src\\images\\" + name), StandardCopyOption.REPLACE_EXISTING);
                System.out.println(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
