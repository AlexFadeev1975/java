package searchengine.services.serviceKit;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexingLauncher implements Runnable {


    public SiteRepository siteRepository;
    public PageRepository pageRepository;
    public LemmaRepository lemmaRepository;
    public IndexRepository indexRepository;
    private LemmaFinder lemmaFinder;
    private IndexBuilder indexBuilder;
    private LinksFinder linksFinder;
    private searchengine.config.Site link;

    public IndexingLauncher(SiteRepository siteRepository, PageRepository pageRepository, LemmaRepository lemmaRepository, IndexRepository indexRepository, LemmaFinder lemmaFinder, IndexBuilder indexBuilder, LinksFinder linksFinder, searchengine.config.Site link) {
        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
        this.lemmaRepository = lemmaRepository;
        this.indexRepository = indexRepository;
        this.lemmaFinder = lemmaFinder;
        this.indexBuilder = indexBuilder;
        this.linksFinder = linksFinder;
        this.link = link;
    }


    @Override
    public void run() {

        if (link != null) {

            lemmaFinder = new LemmaFinder();
            indexBuilder = new IndexBuilder();

            List<Site> siteList = siteRepository.findByUrl(link.getUrl());

            if (!siteList.isEmpty()) {
                int idSite = siteList.get(0).getId();
                List<Page> pageList = pageRepository.findAllByIdSite(idSite);
                List<Integer> pageIdList = new ArrayList<>();
                pageList.forEach(page -> { pageIdList.add(page.getId()); });
                List<Index> indexList = indexRepository.findByPageIdIn(pageIdList);
                indexRepository.deleteAll(indexList);
                siteRepository.deleteById(idSite);
            }
            Site site = new Site();
            site.setName(link.getName());
            site.setUrl(link.getUrl());
            site.setStatus(StatusSite.INDEXING);
            site.setStatusTime(new Date());
            site.setLastError(null);
            Site savedSite = siteRepository.saveAndFlush(site);
            int idSite = savedSite.getId();

            linksFinder = new LinksFinder(site.getUrl());

            List<Page> pages = linksFinder.getPagesFromLinks(LinksFinder.ReadAllLinks.resultLinks, idSite);

            pageRepository.saveAllAndFlush(pages);

            List<Lemma> lemmaList = lemmaFinder.getListPageContents(pages, idSite, pages.size());

            lemmaRepository.saveAllAndFlush(lemmaList);

            List<Page> listPages = pageRepository.findAllByIdSite(idSite);

            List<Lemma> listLemmas = lemmaRepository.findAllByIdSite(idSite);

            List<Index> indexList = indexBuilder.getIndexList(listPages, listLemmas);

            indexRepository.saveAllAndFlush(indexList);
            indexList.clear();

            if (!listPages.isEmpty() && !listLemmas.isEmpty()) {
                site.setStatus(StatusSite.INDEXED);
                site.setLastError("Не обнаружено");
            } else {
                site.setStatus(StatusSite.FAILED);
                site.setLastError("Сайт не содержит контента");
            }

            siteRepository.updateStatusSiteAndStatusTimeAndLastError(site.getStatus(), new Date(), site.getLastError(), site.getId());

        }

    }
}
