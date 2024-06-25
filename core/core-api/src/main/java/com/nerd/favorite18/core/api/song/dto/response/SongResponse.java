package com.nerd.favorite18.core.api.song.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/* Null인 필드는 반환 X */
@Getter
@JsonInclude(Include.NON_NULL)
public class SongResponse {

    private Long songId;
    private String title;
    private String artist;
    private String albumUrl;
    private String lyrics;
    private String scoreCompareUrl;
    private List<SongCodeResponse> machineCodes = new ArrayList<>();
    private Boolean isFavorite;
    @Setter
    private int totalFavoriteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private SongResponse(
        Long songId,
        String title,
        String artist,
        String albumUrl,
        String lyrics,
        String scoreCompareUrl,
        List<SongCodeResponse> machineCodes,
        Boolean isFavorite,
        int totalFavoriteCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.albumUrl = albumUrl;
        this.lyrics = lyrics;
        this.scoreCompareUrl = scoreCompareUrl;
        this.machineCodes = machineCodes;
        this.isFavorite = isFavorite;
        this.totalFavoriteCount = totalFavoriteCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
