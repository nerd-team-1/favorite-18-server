package com.nerd.favorite18.core.api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelScoreResponse {
    String recordedFilename;
    Integer score;
    Double tune;
    Double similarity;

    public static ModelScoreResponse of(String recordedFilename, Integer score, Double tune, Double similarity) {

        return new ModelScoreResponse(
                recordedFilename,
                score,
                tune,
                similarity
        );
    }
}
