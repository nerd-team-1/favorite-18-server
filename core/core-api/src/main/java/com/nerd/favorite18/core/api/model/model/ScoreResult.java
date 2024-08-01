package com.nerd.favorite18.core.api.model.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ScoreResult {
    @JsonProperty("mean_mfcc_similarity")
    private String meanMfccSimilarity;

    @JsonProperty("mean_chroma_similarity")
    private String meanChromaSimilarity;

    @JsonProperty("combined_similarity")
    private String combinedSimilarity;

    @JsonProperty("normalized_score")
    private String normalizedScore;
}
