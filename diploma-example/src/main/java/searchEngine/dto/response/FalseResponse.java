package searchEngine.dto.response;

import lombok.Value;

@Value
public class FalseResponse {
    boolean result;
    String error;
}
