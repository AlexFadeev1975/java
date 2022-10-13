package model;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

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

    public void save (String sqlString) {

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
        public Lemma mapRow (ResultSet rs, int rowNum) throws SQLException {
            Lemma lemma = new Lemma();
            lemma.setId(rs.getInt("id"));
            lemma.setLemma(rs.getString("lemma"));
            lemma.setFrequency(rs.getInt("frequency"));

            return lemma;
        }
    }

    public List<Page> getAllPages () {

        return jdbcTemplate().query("select * from page", new RowPage());

        }

    public List <Lemma> getAllLemmas () {

        return jdbcTemplate().query("select * from lemma", new RowLemma());
    }
    public List <Lemma> getLemmas (List <String> lemmasList) {

        StringBuilder stringQuery = new StringBuilder();
        Iterator<String> iterator = lemmasList.listIterator();
        while (iterator.hasNext()) {
            stringQuery.append(iterator.next());
            if (iterator.hasNext()) {
                stringQuery.append(", ");
            }
        }

        return jdbcTemplate().query("select " + stringQuery + " from lemma", new RowLemma());
    }

    }



