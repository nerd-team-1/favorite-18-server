package com.nerd.favorite18.core.api.model.dto.response;

import com.nerd.favorite18.core.api.song.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelScoreListResponse {
    private Long id;
    private SongDto song;
    private Integer score;
    private Double similarity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ModelScoreListResponse of(
            Long id,
            SongDto song,
            Integer score,
            Double similarity,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new ModelScoreListResponse(
                id,
                song,
                score,
                similarity,
                createdAt,
                updatedAt
        );
    }
}
