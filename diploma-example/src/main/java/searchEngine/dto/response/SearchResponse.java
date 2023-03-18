package searchEngine.dto.response;

import searchEngine.dto.SearchDto;
import lombok.Value;

import java.util.List;

@Value
public class SearchResponse {
    boolean result;
    int count;
    List<SearchDto> data;
}
