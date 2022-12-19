package searchengine.services;

import lombok.Setter;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import searchengine.model.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Morpholog {

    int maxCount = 100;
    @Setter
    public boolean interrupt = false;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Morpholog() {


    }

    public List<String> getLemmas(String text) throws IOException {

        List<String> usefulWords = new ArrayList<>();
        LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
        List<String> listLemmas = new ArrayList<>();
        String clearedText = text.replaceAll("[\"*»*«*\\.*\\,*(*)*\'*]", " ");
        String[] splitText = clearedText.split(" ");
        for (int i = 0; i < splitText.length; i++) {
            if (interrupt) {
                logger.info("морфолога тормознули");
                interrupt = false;
                break;
            }
            String x = splitText[i];

            if (x.matches("[а-яА-ЯёЁ]+")) {
                String normalWord = x.toLowerCase();
                String normalWordTrim = normalWord.trim();
                usefulWords.add(normalWordTrim);
            }
        }
        for (int i = 0; i < usefulWords.size(); i++) {
            if (interrupt) {
                logger.info("люцен тормознули");
                interrupt = false;
                break;
            }
            String x = usefulWords.get(i);
            List<String> listBasedForms = luceneMorphology.getNormalForms(x);
            String basedForm = listBasedForms.get(0);
            List<String> wordInfo = luceneMorphology.getMorphInfo(basedForm);
            String[] info = wordInfo.get(0).split("\\|");
            String[] wordReview = info[1].split(" ");
            if (wordReview[1].matches("С") || (wordReview[1].matches("П")) ||
                    (wordReview[1].matches("Г"))) {
                listLemmas.add(basedForm);
            }
        }
        return listLemmas;
    }

    private List<String> getSingleLemmas(List<String> listLemmas) {
        List<String> listSingleLemmas = new ArrayList<>();
        listLemmas.forEach(l -> {
            if (!listSingleLemmas.contains(l)) {
                listSingleLemmas.add(l);
            }
        });
        return listSingleLemmas;
    }

    public String getListPageContents(List<Page> pages, int idSite) throws InterruptedException {

        HashMap<String, Integer> lemmaHolder = new HashMap<>();
        HashMap<String, Integer> lemmaHolderCleared = new HashMap<>();

        StringBuilder stringQuery = new StringBuilder();
        for (int i = 0; i < pages.size(); i++) {
            if (interrupt) {
                logger.info("лемматизатор тормознули ");
                interrupt = false;
                break;
            }
            Page x = pages.get(i);
            try {
                if ((x.getCode() == 200) && (x.getContent() != null)) {
                    List<String> lemmas = getSingleLemmas(getLemmas(x.getContent()));

                    lemmas.forEach(a -> {
                        int count = (lemmaHolder.containsKey(a)) ? lemmaHolder.get(a) + 1 : 1;
                        lemmaHolder.put(a, count);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        lemmaHolder.forEach((key, value) -> {
            if (value < maxCount) {
                lemmaHolderCleared.put(key, value);

            }

        });

        lemmaHolderCleared.forEach((key, value) -> stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(key).append("',").
                append(value).append(" , ").append(idSite).append(")"));

        return stringQuery.toString();
    }


}

