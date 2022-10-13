package model;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

@Service
public class Morpholog {
    
    public Morpholog() {
        
    }

    public List<String> getLemmas (String text) throws IOException {

        List<String> usefulWords = new ArrayList<>();
        LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
        List <String> listLemmas = new ArrayList<>();

        String [] splitText = text.split(" ");

        Arrays.stream(splitText).forEach(x -> {

            if (x.matches("^[а-яА-ЯёЁ]+$")) {
                String normalWord = x.toLowerCase();
                    normalWord.trim();
                    usefulWords.add(normalWord);
                   }
        });
        usefulWords.forEach(x -> {

            List<String> listBasedForms = luceneMorphology.getNormalForms(x);
            String basedForm = listBasedForms.get(0);
            List<String> wordInfo = luceneMorphology.getMorphInfo(basedForm);
            String[] info = wordInfo.get(0).split("\\|");
            String[] wordReview = info[1].split(" ");
            if (wordReview[1].matches("С") || (wordReview[1].matches("П")) || (wordReview[1].matches("Г"))) {
                if (!listLemmas.contains(basedForm)) {
                listLemmas.add(basedForm);}
            }
        });
     return listLemmas; }

    public void getListPageContents () {

        DbService dbService = new DbService();
        HashMap <String, Integer> lemmaHolder = new HashMap<>();

        List<Page> page = dbService.getAllPages();

        StringBuilder stringQuery = new StringBuilder();
        
        page.forEach(x -> {
            try {
                List <String> lemmas = getLemmas(x.getContent());
                lemmas.forEach(a -> {
                    int count = (lemmaHolder.containsKey(a)) ? lemmaHolder.get(a) + 1 : 0;
                    lemmaHolder.put(a, count);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            lemmaHolder.entrySet().forEach(l -> {
                stringQuery.append((stringQuery.length() < 1) ? "('" : ",('").append(l.getKey()).append("','").
                        append(l.getValue()).append("')");

            });
        
        });
        String sqlString = "INSERT INTO lemma (lemma, frequency) VALUES" +
                stringQuery + ";";
        dbService.save(sqlString);
    }



}

