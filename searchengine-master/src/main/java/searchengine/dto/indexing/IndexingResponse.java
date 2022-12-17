package searchengine.dto.indexing;

import lombok.Data;


@Data
public class IndexingResponse {

    private boolean result;

    String error;

    public IndexingResponse (boolean result) {
        this.result = result;
    }

    public IndexingResponse (boolean result, String error) {
        this.result = result;
        this.error = error;
    }
}
