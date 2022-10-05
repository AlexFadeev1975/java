package model;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;


public class GetLinks {

    public GetLinks(String url) {
        ReadAllLinks links = new ReadAllLinks(url);
        ReadAllLinks.site = url;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Set<String> res = (Set<String>) forkJoinPool.invoke(links);
        printer(res);
        linkStorage(res);
    }
    static class ReadAllLinks extends RecursiveTask {

        public static Set<String> setLinks = new HashSet<String>();
        public static SortedSet<String> resultLinks = new TreeSet<>();
        public static String site;
        public static String line;

        public ReadAllLinks(String line) {

            ReadAllLinks.line = line;
        }

        @Override
        protected Set<String> compute() {

            try {
                Connection.Response response = Jsoup.connect(line).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
                        .referrer("www.google.com").validateTLSCertificates(false).maxBodySize(0).ignoreContentType(true).ignoreHttpErrors(true).timeout(40000).execute();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (response.statusCode() == 200) {
                    Document doc = response.parse();

                    Elements links = doc.select("a");

                    if (links.size() == 1) {
                        String reference = links.attr("abs:href");
                        boolean add = setLinks.add(reference);
                        if (add && reference.contains(site)) {
                            resultLinks.add(reference);
                            new ReadAllLinks(reference);
                        }
                    } else {
                        List<ReadAllLinks> taskList = new ArrayList<>();
                        links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((reference) -> {
                            boolean add = setLinks.add(reference);
                            if (add && reference.contains(site)) {
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
                    System.out.println("код не 200");}

            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultLinks;

        }
    }

    public void printer(Set<String> res) {
        res.stream().forEach(System.out::println);
    }

    public void linkStorage (Set <String> res) {

        StringBuilder stringQuery = new StringBuilder();
        DbService DbService = new DbService();

        res.forEach(x -> {
                    HTMLAnalyzer analyzer = null;
                    try {
                        analyzer = new HTMLAnalyzer(x, ReadAllLinks.site);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    assert analyzer != null;

                    stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(analyzer.getPath()).append("','").
                            append(analyzer.getCode()).append("','").append(analyzer.getContent()).append("')");
                });

                 String sqlString = "INSERT INTO page (path, code, content) VALUES" +
                        stringQuery + ";";
                DbService.save(sqlString);
                stringQuery.setLength(0);
                res.clear();
        }
     }







