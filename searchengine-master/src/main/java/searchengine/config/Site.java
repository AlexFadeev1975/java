package searchengine.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;


@Data
public final class Site {
    private String url;
    private String name;
}
