package searchengine.config;

import lombok.Data;
import lombok.SneakyThrows;
import searchengine.services.GetLinks;
import searchengine.services.Indexer;
import searchengine.services.Morpholog;
import searchengine.services.SearchSystem;

@Data

public class IndexingKit {

    public GetLinks getLinks;

    public Morpholog morpholog;

    public Indexer indexer;

    public SearchSystem searchSystem;
}


