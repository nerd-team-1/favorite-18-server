package com.nerd.favorite18.storage.db.core.song.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class SongQueryDto {
    private Long songId;
    private String title;
    private String artist;
    private String albumUrl;
    private String lyrics;
    private String scoreCompareUrl;
    private int totalFavoriteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SongCodeQueryDto> machineCodes;

    @QueryProjection
    public SongQueryDto(
        Long songId,
        String title,
        String artist,
        String albumUrl,
        String scoreCompareUrl,
        int totalFavoriteCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SongCodeQueryDto> machineCodes
    ) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.albumUrl = albumUrl;
        this.scoreCompareUrl = scoreCompareUrl;
        this.totalFavoriteCount = totalFavoriteCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.machineCodes = machineCodes;
    }

    @QueryProjection
    public SongQueryDto(
        Long songId,
        String title,
        String artist,
        String albumUrl,
        String lyrics,
        String scoreCompareUrl,
        int totalFavoriteCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SongCodeQueryDto> machineCodes
    ) {
        this(songId, title, artist, albumUrl, scoreCompareUrl, totalFavoriteCount, createdAt, updatedAt, machineCodes);
        this.lyrics = lyrics;
    }
}
