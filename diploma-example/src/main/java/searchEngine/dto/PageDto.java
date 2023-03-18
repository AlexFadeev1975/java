package searchEngine.dto;

import lombok.*;


@Value
public class PageDto {
    String url;
    String htmlCode;
    int statusCode;
}
