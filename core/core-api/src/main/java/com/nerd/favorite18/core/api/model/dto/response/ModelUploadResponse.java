package com.nerd.favorite18.core.api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelUploadResponse {
    Long modelScoreId;
    String recordedFilename;

    public static ModelUploadResponse of(Long modelScoreId, String recordedFilename) {
        return new ModelUploadResponse(modelScoreId, recordedFilename);
    }
}
