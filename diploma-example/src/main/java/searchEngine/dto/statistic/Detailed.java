package searchEngine.dto.statistic;

import searchEngine.model.Status;
import lombok.Value;

import java.util.Date;

@Value
public class Detailed {
    String url;
    String name;
    Status status;
    Date statusTime;
    String error;
    Long pages;
    Long lemmas;
}
