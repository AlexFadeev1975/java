package searchengine.services;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.repository.DbService;
import searchengine.services.Morpholog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class Indexer {
    @Setter
    public boolean interrrupt = false;
    Morpholog morpholog = new Morpholog();
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Indexer() throws InterruptedException {
    }

    public String indexer (List<Page> pages, List<Lemma> lemmas) throws IOException {

        StringBuilder stringQuery = new StringBuilder();
        for (Page x : pages) {
            if (interrrupt) {
                logger.info("инексацию тормознули");
                interrrupt = false;
                break; }
            String content = x.getContent();
            if (x.getCode() == 200 && content.contains("zzz")) {
                List<String> titleLemmas = new ArrayList<>();
                List<String> bodyLemmas = new ArrayList<>();
                AtomicInteger idLemma = new AtomicInteger();
                AtomicReference<Float> rank = new AtomicReference(0.0F);
                int pageId = x.getId();

                String[] splitContent = content.split("zzz");
                try {
                    titleLemmas = morpholog.getLemmas(splitContent[0]);
                    bodyLemmas = morpholog.getLemmas(splitContent[1]);
                    List<String> tempList = new ArrayList<>();

                    List<String> finalBodyLemmas = bodyLemmas;
                    titleLemmas.forEach(tl -> {
                        if (!tempList.contains(tl)) {
                            float count = (Collections.frequency(finalBodyLemmas, tl));
                            rank.set((float) (1.0 + count * 0.8));
                            int id = findIdLemma(lemmas, tl);
                            if (id != 0) {
                                idLemma.set(id);
                                stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(idLemma.get()).append("','").
                                        append(pageId).append("','").append(rank.get()).append("')");
                                tempList.add(tl);
                            }
                        }
                    });
                    List<String> finalBodyLemmas1 = bodyLemmas;
                    bodyLemmas.forEach(bl -> {
                        if (!tempList.contains(bl)) {
                            float count = (Collections.frequency(finalBodyLemmas1, bl));
                            rank.set((float) (count * 0.8));
                            int id = findIdLemma(lemmas, bl);
                            if (id != 0) {
                                idLemma.set(id);
                                stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(idLemma.get()).append("','").
                                        append(pageId).append("','").append(rank.get()).append("')");
                            }
                        }

                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

     return stringQuery.toString();
    }
    private int findIdLemma (List <Lemma> lemmas, String lemma) {
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
