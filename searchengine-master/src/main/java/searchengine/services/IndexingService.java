package searchengine.services;

import searchengine.model.ResultPage;

import java.io.IOException;
import java.util.List;

public interface IndexingService {


    void runFullIndexing() throws InterruptedException;

    void stopFullIndexing() throws InterruptedException;

    void oneIndexingSite(searchengine.config.Site link) throws InterruptedException;

    List<ResultPage> searchEngine(String searchString) throws IOException, InterruptedException;
}
