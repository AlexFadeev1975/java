package searchEngine.dto.response;

import searchEngine.dto.statistic.Statistics;
import lombok.Value;

@Value
public class StatisticResponse {
    boolean result;
    Statistics statistics;
}
