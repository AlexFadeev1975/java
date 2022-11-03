package model;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

@Service
public class Morpholog {

    int maxCount = 50;

    public Morpholog() {
        
    }

    public List<String> getLemmas (String text) throws IOException {

        List<String> usefulWords = new ArrayList<>();
        LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
        List <String> listLemmas = new ArrayList<>();


        String [] splitText = text.split(" ");

        Arrays.stream(splitText).forEach(x -> {

            if (x.matches("[а-яА-ЯёЁ]+")) {
                String normalWord = x.toLowerCase();
                  String normalWordTrim = normalWord.trim();
                usefulWords.add(normalWordTrim);
                   }
        });
        usefulWords.forEach(x -> {

            List<String> listBasedForms = luceneMorphology.getNormalForms(x);
            String basedForm = listBasedForms.get(0);
            List<String> wordInfo = luceneMorphology.getMorphInfo(basedForm);
            String[] info = wordInfo.get(0).split("\\|");
            String[] wordReview = info[1].split(" ");
            if (wordReview[1].matches("С") || (wordReview[1].matches("П")) ||
                    (wordReview[1].matches("Г")) ) {
                   listLemmas.add(basedForm);
            }
        });
     return listLemmas; }

    public List <String> getSingleLemmas (List <String> listLemmas) {
        List <String> listSingleLemmas = new ArrayList<>();
        listLemmas.forEach(l -> {
            if (!listSingleLemmas.contains(l)) {
                listSingleLemmas.add(l);
            }
        });
    return listSingleLemmas; }

    public void getListPageContents () {

        DbService dbService = new DbService();
        HashMap <String, Integer> lemmaHolder = new HashMap<>();
        HashMap <String, Integer> lemmaHolderCleared = new HashMap<>();

        List<Page> page = dbService.getAllPages();

        StringBuilder stringQuery = new StringBuilder();
        
        page.forEach(x -> {
            try {
                List <String> lemmas = getSingleLemmas(getLemmas(x.getContent()));

                lemmas.forEach(a -> {
                    int count = (lemmaHolder.containsKey(a)) ? lemmaHolder.get(a) + 1 : 1;
                    lemmaHolder.put(a, count);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        lemmaHolder.forEach((key, value) -> {
            if (value < maxCount) {
                lemmaHolderCleared.put(key, value);
            }
        });

        lemmaHolderCleared.forEach((key, value) -> stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(key).append("','").
                append(value).append("')"));

        String sqlString = "INSERT INTO lemma (lemma, frequency) VALUES" +
                stringQuery + ";";
        dbService.save(sqlString);
    }



}

