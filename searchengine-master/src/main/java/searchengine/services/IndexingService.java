package searchengine.services;

import searchengine.dto.indexing.IndexingResponse;
import searchengine.model.ResultPage;

import java.io.IOException;
import java.util.List;

public interface IndexingService {

//     boolean call();
//    IndexingResponse fullIndexing ();

    boolean runFullIndexing() throws InterruptedException;

    boolean stopFullIndexing() throws InterruptedException;

    void oneIndexingSite (searchengine.config.Site link) throws InterruptedException;

    List<ResultPage> searchEngine(String searchString) throws IOException, InterruptedException;
}
