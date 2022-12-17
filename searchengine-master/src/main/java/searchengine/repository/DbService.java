package searchengine.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import searchengine.model.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class DbService  implements DaoService{


//    public DataSource dataSource() {
//        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//       dataSourceBuilder.url("jdbc:mysql://localhost:3306/search_engine?max_allowed_packet=16777216");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("Alex1975");
//
//       return dataSourceBuilder.build();
//   }
//    @Bean
//    @ConfigurationProperties (prefix = "application.datasource")
// public DataSource dataSource() {
//       return DataSourceBuilder.create().build();
//   }

     private JdbcTemplate jdbcTemplate;
     private SiteRepository seRepository;

     @Autowired
     public DbService (DataSource dataSource) {
         jdbcTemplate = new JdbcTemplate(dataSource);
     }

    public int save(String sqlString) {


        return this.jdbcTemplate.update(sqlString);
    }

    public static class RowPage implements RowMapper<Page> {
        public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
            Page page = new Page();
            page.setId(rs.getInt("id"));
            page.setCode(rs.getInt("code"));
            page.setPath(rs.getString("path"));
            page.setContent(rs.getString("content"));
            page.setIdSite(rs.getInt("id_site"));
            return page;
        }
    }

    public static class RowLemma implements RowMapper<Lemma> {
        public Lemma mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lemma lemma = new Lemma();
            lemma.setId(rs.getInt("id"));
            lemma.setLemma(rs.getString("lemma"));
            lemma.setFrequency(rs.getInt("frequency"));


            return lemma;
        }
    }

    public static class RowIndex implements RowMapper<Index> {
        public Index mapRow(ResultSet rs, int rowNum) throws SQLException {
            Index index = new Index();
            index.setId(rs.getInt("id"));
            index.setLemmaId(rs.getInt("lemma_id"));
            index.setPageId(rs.getInt("page_id"));
            index.setRank(rs.getFloat("rank"));

            return index;
        }

    }

    public static class RowIndexPageLemmaId implements RowMapper<Index> {
        public Index mapRow(ResultSet rs, int rowNum) throws SQLException {
            Index index = new Index();
            index.setId(0);
            index.setLemmaId(rs.getInt("lemma_id"));
            index.setPageId(rs.getInt("page_id"));
            index.setRank(rs.getFloat("rank"));

            return index;
        }

    }
    public static class RowSite implements RowMapper<Site> {
        public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
            Site site = new Site();
            site.setId(rs.getInt("id"));
            site.setStatus(StatusSite.valueOf(rs.getString("status")));
            site.setStatusTime(rs.getDate("status_time"));
            site.setUrl(rs.getString("url"));
            site.setName(rs.getString("name"));
            site.setLastError(rs.getString("last_error"));

            return site;
        }

    }
    @Override
    public List<Page> getAllPages() {

        return jdbcTemplate.query("select * from page", new RowPage());

    }
    @Override
    public List<Lemma> getAllLemmas() {

        return jdbcTemplate.query("select * from lemma", new RowLemma());

    }

    @Override
    public List<Site> getAllSites() {
        return jdbcTemplate.query("select * from site", new RowSite());
    }

    @Override
    public List<Lemma> getLemmas(List<String> lemmasList) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<String> iterator = lemmasList.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append("\"").append(iterator.next()).append("\"");
            if (iterator.hasNext()) {
                stringQuery.append(", ");
            }
        }

        return jdbcTemplate.query("select * from lemma WHERE lemma IN (" + stringQuery + ")", new RowLemma());
    }
    @Override
    public List<Page> getPages(List<Integer> listPageId) {

        if (listPageId.isEmpty()) {
            return new ArrayList<>();
        } else {
            StringBuilder stringQuery = new StringBuilder();
            Iterator<Integer> iterator = listPageId.listIterator();
            while (iterator.hasNext()) {
                stringQuery.append("\"").append(iterator.next()).append("\"");
                if (iterator.hasNext()) {
                    stringQuery.append(", ");
                }
            }

            return jdbcTemplate.query("select * from page WHERE id IN (" + stringQuery + ")", new RowPage());

        }
    }


    @Override
    public List<Index> getPageLemmaIdFromListLemmas(List<Lemma> listLemma) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<Lemma> iterator = listLemma.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append("lemma_id = ").append(iterator.next().getId());
            if (iterator.hasNext()) {
                stringQuery.append(" or ");
            }
        }
        return jdbcTemplate.query("select distinct page_id, lemma_id, `rank` from `index` WHERE " + stringQuery + ";", new RowIndexPageLemmaId());


    }
    @Override
    public int saveSiteReturnID(Site site) {


        jdbcTemplate.update("insert into site (name, url, status, status_time) values (?, ?, ?, ?) ", site.getName(),
                site.getUrl(), site.getStatus().name() , site.getStatusTime());

            return jdbcTemplate.queryForObject("select id from site where url = \"" + site.getUrl() + "\"", Integer.class);

    }


    @Override
    public List<Site> findSiteIndexing() {
        try {
            return jdbcTemplate.query("select * from site where `status` = \"INDEXING\"", new RowSite());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public int findIdSite(String url) {
        try {
            return jdbcTemplate.queryForObject("select id from site where url = \"" + url + "\"", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
    @Override
    public List<Site> findSiteFromUrl (String url) {
        try {
            return jdbcTemplate.query("select * from site where url = \"" + url + "\"", new RowSite());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int saveStatusSite(StatusSite status, int id) {
       return jdbcTemplate.update("update site set `status` = \"" + status.toString() + "\", status_time = now() where id = " + id );
    }

    @Override
    public boolean deleteOneSite(int id) {

        return jdbcTemplate.update("delete from site where id = ?", id) == 1;

        }
}



