import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GetLinks {
    String fileName;

    public GetLinks(String url) {
        readAllLinks links = new readAllLinks(url);
        int i = url.indexOf(".");
        readAllLinks.site = url.substring(i + 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(links);
    }

    static class readAllLinks extends RecursiveAction {

        public static Set<String> setLinks = new HashSet<String>();
        public static Set<String> resultLinks = new TreeSet<>();
        public static String site;
        public static String line;

        public readAllLinks(String line) {

            this.line = line;
        }

        @Override
        protected void compute() {

            try {
                Document doc = Jsoup.connect(line).maxBodySize(0).ignoreHttpErrors(true).get();
                Thread.sleep(150);
                Elements links = doc.select("a");

                if (links.isEmpty()) {
                    return;
                }
                if (links.size() == 1) {
                    String reference = links.attr("abs:href");
                    boolean add = setLinks.add(reference);
                    if (add && reference.contains(site)) {
                        resultLinks.add(reference);
                        System.out.println(reference);
                        new readAllLinks(reference);

                    }
                } else {
                    links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((reference) -> {
                        boolean add = setLinks.add(reference);
                        if (add && reference.contains(site)) {
                            resultLinks.add(reference);
                            System.out.println(reference);
                            new readAllLinks(reference);
                        }
                    });
                }

            } catch (IOException | InterruptedException ex) {
            }
        }
    }

    public void writer(Set<String> list, String filename) {
        this.fileName = filename;
        FileWriter file = null;
        try {
            file = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : list) {
            try {
                file.write(line);
                file.write("\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> comparator(Set<String> list) {

        TreeSet<String> newList = new TreeSet<>(String::compareTo);

        for (String link : list) {
            String[] arr = link.split("/");
            String newlink = tab(arr.length - 3) + link;
            newList.add(newlink);
        }
        return newList.descendingSet();
    }

    public String tab(int count) {
        String tab = "";
        for (int i = 0; i < count; i++) {
            tab = tab + "    ";
        }
        return tab;
    }
}


