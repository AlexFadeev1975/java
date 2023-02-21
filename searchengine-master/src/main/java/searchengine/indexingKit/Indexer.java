package searchengine.indexingKit;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Indexer {

    Morpholog morpholog = new Morpholog();
    List<String> titleLemmas = new ArrayList<>();
    List<String> bodyLemmas = new ArrayList<>();

    List<Index> indexList = new ArrayList<>();
    int pageId;
    public Indexer()  {
    }

    public List<Index> indexer(List<Page> pages, List<Lemma> lemmas)  {

        for (Page x : pages) {
            List<String> tempList = new ArrayList<>();
            String content = x.getContent();
            if (x.getCode() == 200 && content.contains("zzz")) {

                pageId = x.getId();
                String[] splitContent = content.split("zzz");
                try {
                    titleLemmas = morpholog.getLemmas(splitContent[0]);
                    bodyLemmas = morpholog.getLemmas(splitContent[1]);

                    for (String tl : titleLemmas) {
                        indexSet(tl, bodyLemmas, true, lemmas, tempList);
                    }
                    for (String bl : bodyLemmas) {
                        indexSet(bl, bodyLemmas, false, lemmas, tempList);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return indexList;
    }
    private void indexSet (String lemma, List<String> bodyLemmas, boolean lemmaSwitch,  List<Lemma> lemmas, List <String> tempList) {
      if (!tempList.contains(lemma)) {
          float count = (Collections.frequency(bodyLemmas, lemma));
          float rank = (lemmaSwitch) ? ((float) (1.0 + count * 0.8)) : ((float)(count * 0.8)) ;
          int id = findIdLemma(lemmas, lemma);
          if (id != 0) {
              Index index = new Index();
              index.setLemmaId(id);
              index.setPageId(pageId);
              index.setRank(rank);
              indexList.add(index);
              tempList.add(lemma);
          }
      }
    }

    private int findIdLemma(List<Lemma> lemmas, String lemma) {
        AtomicInteger idLemma = new AtomicInteger();
        for (Lemma lemma1 : lemmas) {
            String currentLemma = lemma1.getLemma();
            if (currentLemma.equals(lemma)) {
                idLemma.set(lemma1.getId());
            }
        }
        ;
        return idLemma.get();
    }
}
