package searchengine.services;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.SitesList;
import searchengine.indexingKit.*;
import searchengine.model.Site;
import searchengine.model.StatusSite;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


@Service
@Log4j2
public class IndexingServiceImpl implements IndexingService {

    private final SitesList sitesList;
    @Autowired
    public SiteRepository siteRepository;
    @Autowired
    public PageRepository pageRepository;
    @Autowired
    public LemmaRepository lemmaRepository;
    @Autowired
    public IndexRepository indexRepository;
    private final static int processorsCount = Runtime.getRuntime().availableProcessors();
    private ExecutorService executorService;
    private Morpholog morpholog;
    private Indexer indexer;
    private GetLinks getLinks;
    private SearchSystem searchSystem;
    private searchengine.config.Site link;

    public IndexingServiceImpl(SitesList sitesList) {
        this.sitesList = sitesList;
    }

    @Override
    public boolean runFullIndexing() throws InterruptedException, ExecutionException {

        if (siteRepository.findByStatus(StatusSite.INDEXING).isEmpty()) {
            executorService = Executors.newFixedThreadPool(processorsCount);

            for (int i = 0; i < sitesList.getSites().size(); i++) {
                searchengine.config.Site s = sitesList.getSites().get(i);
                if (executorService.isShutdown()) {
                    break;
                }
                Future future = executorService.submit(new IndexingSite(siteRepository, pageRepository,
                        lemmaRepository, indexRepository, morpholog, indexer, getLinks, s));
                future.get();
            }
            executorService.shutdown();
            if (executorService.isShutdown()) {
                log.info("Индексация завершена");
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean oneIndexingSite(searchengine.config.Site link) throws NullPointerException, ExecutionException, InterruptedException {

        if (siteRepository.findByStatus(StatusSite.INDEXING).isEmpty()) {
            executorService = Executors.newSingleThreadExecutor();
            Future future = executorService.submit(new IndexingSite(siteRepository, pageRepository,
                    lemmaRepository, indexRepository, morpholog, indexer, getLinks, link));
            future.get();
            executorService.shutdown();
            if (executorService.isShutdown()) {
                log.info("Индексация завершена");
            }

            return true;
        } else return false;
    }

    @Override
    public void stopFullIndexing() {

        executorService.shutdownNow();
        log.info("стоп включился");
        if (executorService.isShutdown()) {
            List<Site> siteList = siteRepository.findByStatus(StatusSite.INDEXING);

            if (!siteList.isEmpty()) {
                for (int i = 0; i < siteList.size(); i++) {
                    Site site = siteList.get(i);
                    siteRepository.deleteById(site.getId());
                    site.setStatus(StatusSite.FAILED);
                    site.setStatusTime(new Date());
                    site.setLastError("Индексация страницы была прервана");
                    siteRepository.save(site);
                }
            }
        }
    }
}




