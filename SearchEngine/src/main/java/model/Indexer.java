package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Indexer {

    DbService dbService = new DbService();
    Morpholog morpholog = new Morpholog();

    public void indexer () {

        List<Page> pages = dbService.getAllPages();
        List<Lemma> lemmas = dbService.getAllLemmas();
        StringBuilder stringQuery = new StringBuilder();
        for (Page x : pages) {
            AtomicInteger idLemma = new AtomicInteger();
            AtomicReference<Float> rank = new AtomicReference(Float.valueOf(0.0F));
            int pageId = x.getId();
            String content = x.getContent();
            String[] splitContent = content.split("фф");
            try {
                List<String> titleLemmas = morpholog.getLemmas(splitContent[0]);
                List<String> bodyLemmas = morpholog.getLemmas(splitContent[1]);
                List<String> tempList = new ArrayList<>();

                titleLemmas.forEach(tl -> {
                    if (!tempList.contains(tl)) {
                        float count = (Collections.frequency(bodyLemmas, tl));
                        rank.set((float) (1.0 + count * 0.8));
                        idLemma.set(findIdLemma(lemmas, tl));
                        stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(idLemma.get()).append("','").
                                append(pageId).append("','").append(rank.get()).append("')");
                        tempList.add(tl);
                    }
                });
                bodyLemmas.forEach(bl -> {
                    if (!tempList.contains(bl)) {
                        float count = (Collections.frequency(bodyLemmas, bl));
                        rank.set((float) (count * 0.8));
                        idLemma.set(findIdLemma(lemmas, bl));
                        stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(idLemma.get()).append("','").
                                append(pageId).append("','").append(rank.get()).append("')");
                    }
                });
                tempList.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String sqlString = "INSERT INTO `index` (lemma_id, page_id, `rank`) VALUES" +
                stringQuery + ";";
        dbService.save(sqlString);
    }
    public int findIdLemma (List <Lemma> lemmas, String lemma) {
        AtomicInteger idLemma = new AtomicInteger();
        lemmas.forEach(lemma1 -> {
            String currentLemma = lemma1.getLemma();
            if (currentLemma.equals(lemma)) {
                idLemma.set(lemma1.getId());
            }
        });
        return idLemma.get();
    }
}
