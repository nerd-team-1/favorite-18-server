package com.nerd.favorite18.core.api.ranking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nerd.favorite18.core.api.song.dto.response.SongCodeResponse;
import com.nerd.favorite18.storage.db.core.song.dto.SongCodeQueryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankResponse {

    private Long rankId;
    private String title;
    private String artist;
    private String albumUrl;
    private LocalDate rankDate;
    private String ranking;
    private long searchCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SongCodeQueryDto> songCode;

    @Builder
    private RankResponse(
            Long rankId,
            String title,
            String artist,
            String albumUrl,
            LocalDate rankDate,
            String ranking,
            long searchCnt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<SongCodeQueryDto> songCode
    ) {
        this.rankId = rankId;
        this.title = title;
        this.artist = artist;
        this.albumUrl = albumUrl;
        this.rankDate = rankDate;
        this.ranking = ranking;
        this.searchCnt = searchCnt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.songCode = songCode;
    }
}
