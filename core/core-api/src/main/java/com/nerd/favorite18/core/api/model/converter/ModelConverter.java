package com.nerd.favorite18.core.api.model.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api._common.utils.ConvertUtils;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreResponse;
import com.nerd.favorite18.core.api.model.model.ScoreResult;
import com.nerd.favorite18.storage.db.core.model.entity.ModelScore;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.user.entity.User;

@Converter
public class ModelConverter {
    public ModelScore toEntity(User userEntity, Song songEntity, String recordedFilename) {

        return ModelScore.builder()
                .user(userEntity)
                .song(songEntity)
                .recordedFilename(recordedFilename)
                .build();
    }

    public ModelScoreResponse toResponse(String recordedFilename, ScoreResult scoreResult) {
        final Integer score = ConvertUtils.stringToInteger(scoreResult.getNormalizedScore());
        final Double tune = ConvertUtils.stringToDouble(scoreResult.getMeanChromaSimilarity());
        final Double similarity = ConvertUtils.stringToDouble(scoreResult.getMeanMfccSimilarity());

        return ModelScoreResponse.of(recordedFilename, score, tune, similarity);
    }
}
