package searchEngine.parser;

import searchEngine.dto.IndexDto;
import searchEngine.model.Site;

import java.util.List;

public interface IndexParser {
    void run(Site site);
    List<IndexDto> getIndexList();
}
