package searchengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
@ComponentScan("searchengine")
public class SpringConfig {



    @Bean
    public static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/search_engine?max_allowed_packet=16777216");
       dataSource.setUsername("root");
        dataSource.setPassword("Alex1975");
        return dataSource;
    }
    @Bean
    public static JdbcTemplate jdbcTemplate () {

        return new JdbcTemplate(dataSource());
    }
    @Bean
    public static Site site() {
        return new Site();
    }

    @Bean
    public static IndexingKit indexingKit() throws InterruptedException {
        return new IndexingKit();
    }
    @Bean
    public static OneFullSwitch oneFullSwitch() {
        return new OneFullSwitch();
    }

}



