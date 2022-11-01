package model;
import lombok.NonNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class HTMLAnalyzer {

    String path, link, site;
    int code;
    String content;

    public String getContent() {
        return content;
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public HTMLAnalyzer(String link, String site) throws IOException, InterruptedException {
        this.link = link;
        this.site = site;
        analyser();
    }

    public void analyser () throws IOException, InterruptedException {

        Connection.Response response = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
                .referrer("www.google.com").validateTLSCertificates(false).maxBodySize(0).ignoreHttpErrors(true).ignoreContentType(true).timeout(40000).execute();

        Thread.sleep(150);


        assert response != null;
        if (!response.toString().isEmpty()) {
            @NonNull
            Document doc = response.parse();//IllegalArgumentException string must njt be empty

            code = response.statusCode();

            String linkSite = link.substring(0, site.length());
            if (linkSite.equals(site)) {

                path = link.substring(site.length() - 1);

            }

            String title = doc.title();
           content = title + " zzz "+ doc.body().text() ;
            if (content.contains("'")) {
           String cont = content.replaceAll("'", "");
              content = cont;
//
            }


        } else {
            System.out.println("Код " + response.statusCode());
        }
    }
}
