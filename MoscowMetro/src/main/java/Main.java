import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MoscowMetroParser parser = new MoscowMetroParser();

        parser.parserHtml("https://skillbox-java.github.io");
        parser.print();

    }
}
