import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ImageFinder finder = new ImageFinder();
        finder.parserHtml("https://lenta.ru/", "img.card-big__image", "src");


    }
}
