public class Main {

    public static void main(String[] args) {

        String url = "https://www.skillbox.ru";
        String fileName = "D:/SiteMap.txt";

        GetLinks getLinks = new GetLinks(url);
        getLinks.writer(getLinks.comparator(GetLinks.ReadAllLinks.resultLinks), fileName);


    }
}