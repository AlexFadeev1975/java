package searchengine.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("searchengine")
public class SpringConfig {

    @Bean
    public static Site site() {
        return new Site();
    }


}



