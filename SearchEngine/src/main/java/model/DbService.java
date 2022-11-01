package model;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class DbService {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/search_engine?max_allowed_packet=16777216");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("Alex1975");

        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    public void save(String sqlString) {

        jdbcTemplate().update(sqlString);
    }

    public static class RowPage implements RowMapper<Page> {
        public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
            Page page = new Page();
            page.setId(rs.getInt("id"));
            page.setCode(rs.getInt("code"));
            page.setPath(rs.getString("path"));
            page.setContent(rs.getString("content"));
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


    public List<Page> getAllPages() {

        return jdbcTemplate().query("select * from page", new RowPage());

    }

    public List<Lemma> getAllLemmas() {

        return jdbcTemplate().query("select * from lemma", new RowLemma());
    }

    public List<Lemma> getLemmas(List<String> lemmasList) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<String> iterator = lemmasList.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append("\"").append(iterator.next()).append("\"");
            if (iterator.hasNext()) {
                stringQuery.append(", ");
            }
        }

        return jdbcTemplate().query("select * from lemma WHERE lemma IN (" + stringQuery + ")", new RowLemma());
    }

    public List<Page> getPages(List<Integer> listPageId) {

        if (listPageId.isEmpty()) { return new ArrayList<>();}
        else {
        StringBuilder stringQuery = new StringBuilder();
        Iterator<Integer> iterator = listPageId.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append("\"").append(iterator.next()).append("\"");
            if (iterator.hasNext()) {
                stringQuery.append(", ");
            }
        }

        return jdbcTemplate().query("select * from page WHERE id IN (" + stringQuery + ")", new RowPage());

    }}

    public List<Index> getIndexFromLemmaId(int lemmaId) {

               return jdbcTemplate().query("select * from `index` WHERE lemma_id = " + lemmaId, new RowIndex());

    }

    public List<Index> getIndexFromPageId (List <Integer> listPageId) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<Integer> iterator = listPageId.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append(iterator.next());
            if (iterator.hasNext()) {
                stringQuery.append(", ");
            }
        }


        return jdbcTemplate().query("select * from `index` WHERE page_id IN(" + stringQuery + ")", new RowIndex());
    }

    public List<Index> getPageLemmaIdFromListLemmas (List <Lemma> listLemma) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<Lemma> iterator = listLemma.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append ("lemma_id = ").append(iterator.next().getId());
            if (iterator.hasNext()) {
                stringQuery.append(" or ");
            }
        }
        return jdbcTemplate().query("select distinct page_id, lemma_id, `rank` from `index` WHERE " + stringQuery + ";", new RowIndexPageLemmaId());



    }
}



