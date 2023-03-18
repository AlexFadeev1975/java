package searchEngine.dto;

import lombok.Value;

@Value
public class LemmaDto {
    String lemma;
    int frequency;
}
