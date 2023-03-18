package searchEngine.service.impl;

import searchEngine.config.IndexConfig;
import searchEngine.model.Site;
import searchEngine.model.Status;
import searchEngine.parser.IndexParser;
import searchEngine.parser.LemmaParser;
import searchEngine.parser.SiteIndex;
import searchEngine.repository.IndexRepository;
import searchEngine.repository.LemmaRepository;
import searchEngine.repository.PageRepository;
import searchEngine.repository.SiteRepository;
import searchEngine.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexServiceImpl implements IndexService {

    private final IndexConfig indexConfig;
    private final SiteRepository siteRepository;
    private static final int processorCoreCount = Runtime.getRuntime().availableProcessors();
    private ExecutorService executorService;
    private final PageRepository pageRepository;
    private final LemmaParser lemmaParser;
    private final LemmaRepository lemmaRepository;
    private final IndexParser indexParser;
    private final IndexRepository indexRepository;


    @Override
    public boolean indexUrl(String url) {
        if (urlCheck(url)) {
            executorService = Executors.newFixedThreadPool(processorCoreCount);
            executorService.submit(new SiteIndex(pageRepository,
                    lemmaParser,
                    lemmaRepository,
                    indexParser,
                    indexRepository,
                    siteRepository,
                    url,
                    indexConfig));
            executorService.shutdown();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean indexAll() {
        if (isIndexingActive()) {
            return false;
        } else {
            var urlList = indexConfig.getSite();
            executorService = Executors.newFixedThreadPool(processorCoreCount);
            for (Map<String, String> map : urlList) {
                String url = map.get("url");
                executorService.submit(new SiteIndex(pageRepository,
                        lemmaParser,
                        lemmaRepository,
                        indexParser,
                        indexRepository,
                        siteRepository,
                        url,
                        indexConfig));
            }
            executorService.shutdown();
            return true;
        }
    }

    @Override
    public boolean stopIndexing() {
        if (isIndexingActive()) {
            log.info("Останавливаем индексацию");
            executorService.shutdownNow();
            return true;
        } else {
            return false;
        }
    }

    private boolean isIndexingActive() {
        var siteList = siteRepository.findAll();
        for (Site site : siteList) {
            if (site.getStatus() == Status.INDEXING) {
                return true;
            }
        }
        return false;
    }

    private boolean urlCheck(String url) {
        var urlList = indexConfig.getSite();
        for (Map<String, String> map : urlList) {
            if (map.get("url").equals(url)) {
                return true;
            }
        }
        return false;
    }
}
