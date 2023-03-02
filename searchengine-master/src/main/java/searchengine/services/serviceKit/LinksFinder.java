package searchengine.services.serviceKit;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import searchengine.model.Page;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


@Data
@Log4j2
public class LinksFinder {

    ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Setter
    public String url;


    public LinksFinder(String url) {
        this.url = url;
        ReadAllLinks links = new ReadAllLinks(this.url);
        ReadAllLinks.site = clearLine(this.url);
        ReadAllLinks.setLinks = new HashSet<>();
        ReadAllLinks.resultLinks = new TreeSet<>();
        this.forkJoinPool = new ForkJoinPool();
        Set<String> res = forkJoinPool.invoke(links);
        printer(res);
    }

    public static class ReadAllLinks extends RecursiveTask<Set<String>> {

        public static Set<String> setLinks;
        public static SortedSet<String> resultLinks;
        public static String site;
        public static String line;


        public ReadAllLinks(String line) {

            ReadAllLinks.line = line;
        }

        @Override
        protected Set<String> compute() {

            try {
                Connection.Response response = Jsoup.connect(line).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
                        .referrer("www.google.com").validateTLSCertificates(false).maxBodySize(0).ignoreContentType(true).ignoreHttpErrors(true).timeout(120000).execute();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Document doc;

                if (response.statusCode() == 200) {
                    try {
                        doc = response.parse();
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                    @NonNull
                    Elements links = doc.select("a");

                    if (links.size() == 1) {
                        String reference = links.attr("abs:href");

                        if (setLinks.add(reference) && reference.contains(site)) {
                            resultLinks.add(reference);
                            new ReadAllLinks(reference);
                        }
                    } else {
                        List<ReadAllLinks> taskList = new ArrayList<>();
                        links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((reference) -> {

                            if (setLinks.add(reference) && ((reference.contains("://" + site)) || (reference.contains("://www." + site)))) {
                                resultLinks.add(reference);
                                ReadAllLinks task = new ReadAllLinks(reference);
                                task.fork();
                                taskList.add(task);
                            }
                        });
                        for (ReadAllLinks task : taskList) {
                            task.join();
                        }
                    }
                } else {
                    log.info("Code " + response.statusCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultLinks;
        }
    }

    private void printer(Set<String> res) {
        res.forEach(System.out::println);
    }

    public List<Page> getPagesFromLinks(Set<String> res, int idSite) {

        List<Page> pages = new ArrayList<>();
        for (String x : res) {
            try {
                HTMLParser parser = new HTMLParser(x, ReadAllLinks.site);
                if ((parser.getPath() != null) & (parser.getContent() != null)) {
                    Page page = new Page();
                    page.setPath(parser.getPath());
                    page.setCode(parser.getCode());
                    page.setContent(parser.getContent());
                    page.setIdSite(idSite);
                    pages.add(page);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return pages;
    }

    private String clearLine(String line) {

        String[] splitLines = line.split("://");
        if (splitLines.length > 1) {
            String[] splitLinesNext = splitLines[1].split("\\.");
            if (splitLinesNext.length > 2) {
                return splitLinesNext[1] + splitLinesNext[2];
            } else return splitLines[1];
        } else return line;
    }
}







