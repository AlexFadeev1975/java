package model;

import org.tartarus.snowball.ext.RussianStemmer;
import java.io.IOException;
import java.util.*;

public class SearchSystem {


    public SearchSystem() throws IOException {
        String searchString = "мама мыла машу";
        pageIdAndRelRankFinder(listLemmas(searchString));
    }

    String searchString;

    public List<Lemma> listLemmas(String searchString) throws IOException {

        DbService dbService = new DbService();
        Morpholog morpholog = new Morpholog();
        List<Lemma> lemmaList = dbService.getLemmas(morpholog.getLemmas(searchString));
        lemmaList.sort(new Comparator<Lemma>() {
            @Override
            public int compare(Lemma o1, Lemma o2) {
                if (o1.getFrequency() > o2.getFrequency()) return 1;
                if (o1.getFrequency() < o2.getFrequency()) return -1;
                return 0;
            }
        });
        return lemmaList;

    }


    public List<ResultPage> pageIdAndRelRankFinder(List<Lemma> lemmaList) {

        if (!lemmaList.isEmpty()) {
        DbService dbService = new DbService();
        List<ResultPage> resultPageList = new ArrayList<>();

        List<Index> mapPageIdLemmaId = dbService.getPageLemmaIdFromListLemmas(lemmaList);

        HashMap<Integer, Float> mapPadeIdRank = new HashMap<>();
        List <Integer> listPageId = new ArrayList<>();

        mapPageIdLemmaId.forEach(x -> {
               listPageId.add(x.getPageId());
            if (Collections.frequency(listPageId, x.getPageId()) == lemmaList.size()) {
                int pageId = x.getPageId();
                float rank = x.getRank();
                float absRank = (mapPadeIdRank.containsKey(pageId)) ? mapPadeIdRank.get(pageId) + rank : rank;
                mapPadeIdRank.put(pageId, absRank);
            }

        });
       assert (mapPadeIdRank != null);
        mapPadeIdRank.forEach((pageId, rank) -> {
            mapPadeIdRank.put(pageId, (rank / Collections.max(mapPadeIdRank.values())));
        });
        List<Page> resultPages = dbService.getPages(mapPadeIdRank.keySet().stream().toList());
        resultPages.forEach(page -> {
            ResultPage resultPage = new ResultPage();
            resultPage.setUrl(page.getPath());
            String[] arrContent = page.getContent().split("zzz");
            resultPage.setTitle(arrContent[0]);
            resultPage.setSnippet(getSnippet(arrContent[1], lemmaList));//
            resultPage.setRelevance(mapPadeIdRank.get(page.getId()));
            resultPageList.add(resultPage);
        });


        return resultPageList;
        } else return null;

    }

    public String getStem (String word) {

        RussianStemmer stemmer = new RussianStemmer();
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    public String getSnippet (String content, List <Lemma> lemmas) {
        String[] arrContent = content.split(" ");
        List <String> stemList = new ArrayList<>();
        String snippet = "";
        int startWord = arrContent.length;
        int endWord = 0;
        lemmas.forEach(l -> {
            stemList.add(getStem(l.getLemma()));
        });
        for (int i = 0; i < arrContent.length; i++) {
            if (stemList.contains(getStem(arrContent[i]))) {
                stemList.remove(getStem(arrContent[i]));
                startWord = Math.min(startWord, i);
                startWord = (startWord != 0) ? startWord - 1 : startWord;
                endWord = Math.max(endWord, i);
                endWord = (endWord != arrContent.length - 1) ? endWord + 2: endWord;
                arrContent[i] = "<b"+arrContent[i]+"/b>";
            }
            }
        String [] arrSnippet = Arrays.copyOfRange(arrContent, startWord, endWord);
        snippet = String.join(" ", arrSnippet);
    return snippet; }


}
