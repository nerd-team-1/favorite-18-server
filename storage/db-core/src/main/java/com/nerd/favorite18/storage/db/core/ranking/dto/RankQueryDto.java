package com.nerd.favorite18.storage.db.core.ranking.dto;

import com.nerd.favorite18.storage.db.core.song.dto.SongCodeQueryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RankQueryDto {
    private Long id;
    private String title;
    private String artist;
    private String albumUrl;
    private LocalDate rankDate;
    private String ranking;
    private long searchCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String songCode;

    @QueryProjection
    public RankQueryDto(
        Long id,
        String title,
        String artist,
        String albumUrl,
        LocalDate rankDate,
        String ranking,
        long searchCnt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String songCode
    ) {
        this.id = id;
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
