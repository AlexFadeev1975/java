package searchengine.repository;

import searchengine.model.*;

import java.util.List;

public interface DaoService {


    int save(String sqlString);

    List<Page> getAllPages();

    List<Lemma> getAllLemmas();

    List<Site> getAllSites();

    List<Lemma> getLemmas(List<String> lemmasList);

    List<Page> getPages(List<Integer> listPageId);

    List<Index> getPageLemmaIdFromListLemmas(List<Lemma> listLemma);

    int saveSiteReturnID(Site site);

    List<Site> findSiteIndexing();

    int findIdSite(String url);

    int saveStatusSite(StatusSite status, int id);

    List<Site> findSiteFromUrl(String url);

    boolean deleteOneSite(int id);

    List<Lemma> findAllLemmasFromIdSite(int idSite);

    List<Site> findSiteFromName(String name);

    List<Page> findAllPagesByIdSite(int idSite);
}
