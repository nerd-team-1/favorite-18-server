package com.nerd.favorite18.core.api.ranking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nerd.favorite18.core.api.song.dto.response.SongCodeResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/* Null 인 필드는 반환 X */
@Getter
@JsonInclude(Include.NON_NULL)
public class SongRankDto {
    private Long songId;
    private String title;
    private String artist;
    private String albumUrl;
    private List<SongCodeResponse> machineCodes;
    @Setter
    private int totalFavoriteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private SongRankDto(
            Long songId,
            String title,
            String artist,
            String albumUrl,
            List<SongCodeResponse> machineCodes,
            int totalFavoriteCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.albumUrl = albumUrl;
        this.machineCodes = machineCodes;
        this.totalFavoriteCount = totalFavoriteCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
