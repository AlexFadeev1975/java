package searchengine.controllers;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.indexing.IndexingResponse;
import searchengine.dto.indexing.SearchResponse;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.ResultPage;
import searchengine.services.IndexingService;
import searchengine.services.SearchService;
import searchengine.services.StatisticsService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@Log4j2
@RequestMapping("/api")
public class ApiController extends Thread {

    private final StatisticsService statisticsService;
    private static int isRun = 0;
    private final SearchService searchService;
    private final IndexingService indexingService;
        Site site;
    SitesList sitesList;


    public ApiController(StatisticsService statisticsService, IndexingService indexingService, SitesList sitesList, SearchService searchService, Site site) {
        this.statisticsService = statisticsService;
        this.sitesList = sitesList;
        this.indexingService = indexingService;
        this.site = site;
        this.searchService = searchService;

    }

    @GetMapping("/statistics")

    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexingResponse> startIndexing() {

        ApiController api = new ApiController(statisticsService, indexingService, sitesList, searchService, site);

        IndexingResponse indexingResponse = null;

        if (isRun == 0) {
            isRun = 1;
            api.start();

            indexingResponse = new IndexingResponse(true);
            return new ResponseEntity<>(indexingResponse, HttpStatus.OK);
        } else {
            indexingResponse = new IndexingResponse(false, "Индексация уже запущена");
            return new ResponseEntity<>(indexingResponse, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/indexPage")
    public ResponseEntity<IndexingResponse> startOneSiteIndexing(@RequestBody String url) throws InterruptedException, IOException, ExecutionException {
        IndexingResponse indexingResponse = null;

        if (!url.isEmpty()) {
            String result = java.net.URLDecoder.decode(url, StandardCharsets.UTF_8);
            String realUrl = result.substring(4);
            List<Site> sites = sitesList.getSites();
            for (Site value : sites) {
                if (value.getUrl().equals(realUrl)) {
                    site = value;
                    break;
                } else site = null;
            }

            if (site == null) {
                return new ResponseEntity<>(new IndexingResponse(false, "Данная страница находится за пределами сайтов, " +
                        "указанных в конфигурационном файле"), HttpStatus.NOT_FOUND);
            } else {
                ApiController api = new ApiController(statisticsService, indexingService, sitesList, searchService, site);

                if (isRun == 0) {
                    isRun = 2;
                   api.start();

                    indexingResponse = new IndexingResponse(true);

                    return ResponseEntity.ok(indexingResponse);

                } else {
                    return new ResponseEntity<>(new IndexingResponse(false, "Индексация уже запущена"), HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
        } return new ResponseEntity<>( new IndexingResponse(false, "Страница не введена"), HttpStatus.BAD_REQUEST);
    }



    @GetMapping("/stopIndexing")
    public ResponseEntity<IndexingResponse> stopIndexing() throws InterruptedException {
        IndexingResponse indexingResponse = null;
        if (isRun != 0) {

            log.info("прерывание включено");
            ApiController.this.interrupt();

            indexingService.stopFullIndexing();
            isRun = 0;
            return new ResponseEntity<>( new IndexingResponse(true), HttpStatus.OK);

        } else {
           return new ResponseEntity<>(new IndexingResponse(false, "Индексация не запущена"), HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(String query,
                                                 String site,
                                                 int offset,
                                                 int limit) throws IOException {
        if (!query.isEmpty()) {
            List<ResultPage> resultPageList = searchService.searchEngine(query, site);
            if (!(resultPageList == null)) {
                int countPages = resultPageList.size();
                if (offset < resultPageList.size()) {
                    List<ResultPage> totalPageList = resultPageList.subList(offset, resultPageList.size());
                    if (totalPageList.size() > limit) {
                        resultPageList = totalPageList.subList(0, limit);
                    } else {
                        resultPageList = totalPageList;
                    }
                }
                return new ResponseEntity<>(new SearchResponse(true, countPages,
                        resultPageList), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new SearchResponse(true, 0, resultPageList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SearchResponse(false, "Задан пустой поисковый запрос"), HttpStatus.BAD_REQUEST);


        }
    }

    @SneakyThrows
    @Override
    public void run() {

       if (isRun == 1) {
           indexingService.runFullIndexing();
           isRun = 0;
       }
       if (isRun == 2) {
           indexingService.oneIndexingSite(site);
           isRun = 0;
        }
    }

}
