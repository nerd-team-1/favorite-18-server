package com.nerd.favorite18.core.api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelScoreResponse {
    String recordedFilename;
    Integer score;
    Double similarity;

    public static ModelScoreResponse of(String recordedFilename, Integer score, Double similarity) {

        return new ModelScoreResponse(
                recordedFilename,
                score,
                similarity
        );
    }
}
