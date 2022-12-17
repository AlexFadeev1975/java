package searchengine.repository;

import searchengine.model.*;

import javax.sql.DataSource;
import java.util.List;

public interface DaoService {



     int save(String sqlString);

     List<Page> getAllPages();

     List<Lemma> getAllLemmas();

     List <Site> getAllSites();

     List<Lemma> getLemmas(List<String> lemmasList);

     List<Page> getPages(List<Integer> listPageId);

     List<Index> getPageLemmaIdFromListLemmas(List<Lemma> listLemma);

     int saveSiteReturnID(Site site);

     List<Site> findSiteIndexing();

    public int findIdSite(String url);

    public int saveStatusSite(StatusSite status, int id);

    public List<Site> findSiteFromUrl (String url);

    public boolean deleteOneSite(int id);
}
