package searchengine.controllers;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.config.OneFullSwitch;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.indexing.IndexingResponse;
import searchengine.dto.indexing.SearchResponse;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.ResultPage;
import searchengine.services.IndexingService;
import searchengine.services.StatisticsService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiController extends Thread {

    private final StatisticsService statisticsService;
    private static boolean isRun = true;
    boolean interrupt = false;
    OneFullSwitch oneFullSwitch;
    Site site;
    SitesList sitesList;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ApiController(StatisticsService statisticsService, IndexingService indexingService, SitesList sitesList, OneFullSwitch oneFullSwitch, Site site) {
        this.statisticsService = statisticsService;
        this.sitesList = sitesList;
        this.indexingService = indexingService;
        this.oneFullSwitch = oneFullSwitch;
        this.site = site;
    }

    private final IndexingService indexingService;


    @GetMapping("/statistics")

    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexingResponse> startIndexing() throws InterruptedException {
        oneFullSwitch.setSwitcher(1);
        ApiController api = new ApiController(statisticsService, indexingService, sitesList, oneFullSwitch, site);

        IndexingResponse indexingResponse = null;

        if (isRun) {
            api.start();
            isRun = false;
            indexingResponse = new IndexingResponse(true);
            return new ResponseEntity<>(indexingResponse, HttpStatus.OK);
        } else {
            indexingResponse = new IndexingResponse(false, "Индексация уже запущена");
            return new ResponseEntity<>(indexingResponse, HttpStatus.METHOD_NOT_ALLOWED);
        }

//        if (indexingResponse.isResult()) {
//            return ResponseEntity.ok(indexingResponse);
//        } else return new ResponseEntity<>(indexingResponse, HttpStatus.FORBIDDEN);


    }

    @PostMapping("/indexPage")
    public ResponseEntity<IndexingResponse> startOneSiteIndexing(@RequestBody String url) throws InterruptedException {
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
                indexingResponse = new IndexingResponse(false, "Данная страница находится за пределами сайтов, \n" +
                        "указанных в конфигурационном файле");
            } else {

                oneFullSwitch.setSwitcher(2);
                ApiController api = new ApiController(statisticsService, indexingService, sitesList, oneFullSwitch, site);
                if (isRun) {
                    api.start();
                    isRun = false;
                    indexingResponse = new IndexingResponse(true);
                    return ResponseEntity.ok(indexingResponse);
                } else {
                    indexingResponse = new IndexingResponse(false, "Индексация уже запущена");

                }
            }
        }
        if (indexingResponse.isResult()) {
            return ResponseEntity.ok(indexingResponse);
        } else return new ResponseEntity<>(indexingResponse, HttpStatus.FORBIDDEN);

    }


    @GetMapping("/stopIndexing")
    public ResponseEntity<IndexingResponse> stopIndexing() throws InterruptedException {
        IndexingResponse indexingResponse = null;
        if (!isRun) {

            logger.info("прерывание включено");
            interrupt = true;
            isRun = true;
            ApiController.this.interrupt();
            indexingService.stopFullIndexing();


            indexingResponse = new IndexingResponse(true);


        } else {
            indexingResponse = new IndexingResponse(false, "Индексация не запущена");
        }
        if (indexingResponse.isResult() && ApiController.this.isInterrupted()) {
            return ResponseEntity.ok(indexingResponse);
        } else return new ResponseEntity<>(indexingResponse, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam String query) throws IOException, InterruptedException {

        SearchResponse searchResponse;

        if (!query.isEmpty()) {
            List<ResultPage> resultPageList = indexingService.searchEngine(query);
            searchResponse = (!(resultPageList == null)) ? new SearchResponse(true, resultPageList.size(),
                    resultPageList) : new SearchResponse(false, "Совпадений не найдено");
        } else searchResponse = new SearchResponse(false, "Задан пустой поисковый запрос");

        if (searchResponse.isResult()) {
            return ResponseEntity.ok(searchResponse);
        } else return new ResponseEntity<>(searchResponse, HttpStatus.FORBIDDEN);
    }

    @SneakyThrows
    @Override
    public void run() {

        if (oneFullSwitch.getSwitcher() == 1) {
            indexingService.runFullIndexing();
            isRun = true;
        }
        if (oneFullSwitch.getSwitcher() == 2) {
            indexingService.oneIndexingSite(site);
            isRun = true;

        }
    }

}
