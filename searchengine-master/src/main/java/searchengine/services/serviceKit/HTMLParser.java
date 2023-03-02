package searchengine.services.serviceKit;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Log4j2
public class HTMLParser {

    String path, link, site;
    int code;
    String content;

    public HTMLParser(String link, String site) throws IOException, InterruptedException {
        this.link = link;
        this.site = site;
        getContentCodePathFromLink();
    }

    public String getContent() {
        return content;
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public void getContentCodePathFromLink() throws IOException {
        @NonNull
        Connection.Response response;

        response = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
                .referrer("www.google.com").validateTLSCertificates(false).maxBodySize(0).ignoreHttpErrors(true).ignoreContentType(true).timeout(120000).execute();

        code = response.statusCode();

        if ((code == 200) || (!link.contains(".pdf")) || (!link.contains(".jpg")) || (!link.contains(".mp4"))) {

            try {
                Document doc = response.parse();

                if (doc != null & link.getBytes().length < 300) {
                    String[] splitlinkBySite = link.split(site);
                    if (splitlinkBySite.length > 1) {
                        path = splitlinkBySite[1];
                    } else path = link;

                    String title = doc.title();
                    content = title + " zzz " + doc.body().text();
                    if (content.contains("'")) {
                        String cont = content.replaceAll("'", "");
                        content = cont;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.info("пустая ссылка");
            }

        } else {

            log.info(link + " - code " + response.statusCode());
        }
    }

}
