package searchengine.services;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.IndexingKit;
import searchengine.config.SitesList;
import searchengine.model.*;
import searchengine.repository.DaoService;
import searchengine.repository.SiteRepository;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
public class IndexingServiceImpl implements IndexingService {

    private final SitesList  sitesList;

    @Autowired
    public DaoService dbService;
    @Autowired
    public SiteRepository seRepository;
    @Setter
    private boolean interrupt = false;

    private final IndexingKit indexingKit;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public IndexingServiceImpl(SitesList sitesList, IndexingKit indexingKit) {
        this.sitesList = sitesList;
        this.indexingKit = indexingKit;
    }

    @Override
    public void runFullIndexing() throws InterruptedException {

        interrupt = false;
        for (int i = 0; i < sitesList.getSites().size(); i++) {
            if (interrupt) {
                logger.info("полную индексацию прервали");
                interrupt = false;
                break;
            }
            searchengine.config.Site s = sitesList.getSites().get(i);
            oneIndexingSite(s);
        }

    }

    public void oneIndexingSite(searchengine.config.Site link) throws InterruptedException, NullPointerException {

        if (link != null) {
            indexingKit.setMorpholog(new Morpholog());
            Morpholog morpholog = indexingKit.getMorpholog();
            indexingKit.setIndexer(new Indexer());
            Indexer indexer = indexingKit.getIndexer();

            List<Site> siteList = dbService.findSiteFromUrl(link.getUrl());

            if (!siteList.isEmpty()) {
                int idSite = siteList.get(0).getId();
                seRepository.deleteById(idSite);
            }
            Site site = new Site();
            site.setName(link.getName());
            site.setUrl(link.getUrl());
            site.setStatus(StatusSite.INDEXING);
            site.setStatusTime(new Date());
            site.setLastError(null);

            int idSite = dbService.saveSiteReturnID(site);
            indexingKit.setGetLinks(new GetLinks(site.getUrl()));
            GetLinks getLinks = indexingKit.getGetLinks();

            List<Page> pages = getLinks.linkStorage(GetLinks.ReadAllLinks.resultLinks);
            String resultLinks = new GetLinks().makeSqlString(pages, idSite);
            String sqlStringPage = "INSERT INTO page (path, code, content, id_site) VALUES " +
                    resultLinks + ";";
            if (!interrupt) {
                dbService.save(sqlStringPage);
            }

            String stringQueryLemma = null;
            try {
                stringQueryLemma = morpholog.getListPageContents(pages, idSite, pages.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String sqlStringLemma = "INSERT INTO lemma (lemma, frequency, id_site) VALUES" +
                    stringQueryLemma + ";";
            if (!interrupt) {
                dbService.save(sqlStringLemma);
            }

            List<Page> listPages = dbService.getAllPages();
            List<Lemma> listLemmas = dbService.getAllLemmas();
            try {
                String sqlStringIndex = indexer.indexer(listPages, listLemmas);
                String sqlString = "INSERT INTO `index` (lemma_id, page_id, `rank`) VALUES" +
                        sqlStringIndex + ";";
                if (!interrupt) {
                    dbService.save(sqlString);
                }

                if (!listPages.isEmpty() && !listLemmas.isEmpty()) {
                    site.setStatus(StatusSite.INDEXED);
                } else {
                    site.setStatus(StatusSite.FAILED);
                }
                site.setStatusTime(new Date());
                if (!interrupt) {
                    dbService.saveStatusSite(site.getStatus(), idSite);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void stopFullIndexing() {


        interrupt = true;

        logger.info("стоп включился");
        if (indexingKit.getGetLinks() != null) {
            indexingKit.getGetLinks().setInterrupt(true);
        }
        if (indexingKit.getMorpholog() != null) {
            indexingKit.getMorpholog().setInterrupt(true);
        }
        if (indexingKit.getIndexer() != null) {
            indexingKit.getIndexer().setInterrrupt(true);
        }

        logger.info("прерыватели включены");
        List<Site> siteList = dbService.findSiteIndexing();


        if (!siteList.isEmpty()) {
            Site site = siteList.get(0);
             seRepository.deleteById(site.getId());
             site.setStatus(StatusSite.FAILED);
             site.setStatusTime(new Date());
             site.setLastError("Индексация страницы была прервана");
             seRepository.save(site);
        }

    }

    public List<ResultPage> searchEngine(String searchString) throws IOException, InterruptedException {
        indexingKit.setMorpholog(new Morpholog());
        Morpholog morpholog = indexingKit.getMorpholog();
        List<Lemma> lemmaList = dbService.getLemmas(morpholog.getLemmas(searchString));
        List<Site> siteList = dbService.findSiteIndexing();
        if (!lemmaList.isEmpty() & siteList.isEmpty()) {
            lemmaList.sort(Comparator.comparingInt(Lemma::getFrequency));

            List<Index> indexList = dbService.getPageLemmaIdFromListLemmas(lemmaList);
            indexingKit.setSearchSystem(new SearchSystem());
            HashMap<Integer, Float> mapPageIdToRank = indexingKit.getSearchSystem().pageIdAndRelRankFinder(indexList, lemmaList);

            List<Page> resultPages = dbService.getPages(mapPageIdToRank.keySet().stream().toList());
            List<Site> listSites = dbService.getAllSites();
            return indexingKit.getSearchSystem().getResultPages(mapPageIdToRank, resultPages, lemmaList, listSites);
        } else return null;

    }
}




