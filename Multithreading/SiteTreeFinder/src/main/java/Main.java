import java.io.IOException;

public class Main {

    public static void main(String[] args)  {

        String url = "https://www.lenta.ru";
        String fileName = "D:/SiteMap.txt";

        GetLinks getLinks = new GetLinks(url);
        getLinks.writer(getLinks.comparator(GetLinks.readAllLinks.resultLinks), fileName);


    }
}