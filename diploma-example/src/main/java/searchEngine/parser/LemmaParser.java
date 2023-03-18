package searchEngine.parser;

import searchEngine.dto.LemmaDto;
import searchEngine.model.Site;

import java.util.List;

public interface LemmaParser {
    void run(Site site);
    List<LemmaDto> getLemmaDtoList();
}
