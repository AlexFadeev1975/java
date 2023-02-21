package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.indexingKit.Morpholog;
import searchengine.indexingKit.SearchSystem;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    public SiteRepository siteRepository;
    @Autowired
    public PageRepository pageRepository;
    @Autowired
    public LemmaRepository lemmaRepository;
    @Autowired
    public IndexRepository indexRepository;

    @Override
    public List<ResultPage> searchEngine(String searchString, String site) throws IOException {

        Morpholog morpholog = new Morpholog();
        SearchSystem searchSystem = new SearchSystem();
        List<Site> listSites = new ArrayList<>();
        List<Lemma> lemmaList = lemmaRepository.findByLemmaIn(morpholog.getLemmas(searchString));
        HashMap<Integer, Double> mapPageIdToRank = new HashMap<>();
        List<Site> siteList = siteRepository.findByStatus(StatusSite.INDEXING);

        if (!lemmaList.isEmpty() & siteList.isEmpty()) {

            int count = (lemmaList.stream().map(Lemma::getLemma)).collect(Collectors.toSet()).size();

            List<Object[]> idPageListAndAbsRank = indexRepository.findPageIdAndRankFromLemmaIdWithCountLemmaId(lemmaList.stream().map(Lemma::getId).toList(), count);
            idPageListAndAbsRank.forEach(x -> {
                mapPageIdToRank.put((int) x[0], (double) x[1]);
            });

            List<Page> resultPages = pageRepository.findAllById(mapPageIdToRank.keySet().stream().toList());

            if (site != null) {
                List<Site> listSite = siteRepository.findByUrl(site);
                Site findedSite = listSite.get(0);
                if (findedSite == null) {
                    return new ArrayList<>();
                } else {
                    listSites.add(findedSite);
                    return searchSystem.getResultPages(mapPageIdToRank, resultPages, lemmaList, listSites);
                }
            } else {
                listSites = siteRepository.findAll();

                return searchSystem.getResultPages(mapPageIdToRank, resultPages, lemmaList, listSites);
            }
        } else return null;

    }
}

