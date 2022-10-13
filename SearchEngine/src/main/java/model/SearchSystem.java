package model;

import java.io.IOException;
import java.util.List;

public class SearchSystem {

    String searchString;

    public List<String> listLemmas (String searchString) throws IOException {

        Morpholog morpholog = new Morpholog();
        List <String> lemmas = morpholog.getLemmas(searchString);

    }
}
