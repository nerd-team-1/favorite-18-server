package com.nerd.favorite18.core.api.ranking.dto;

import com.nerd.favorite18.storage.db.core.song.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {
    private Long id;
    private Song rankSong;
    private LocalDate rankDate;
    private String rank;
    private Long searchCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RankDto of(
            Long id,
            Song rankSong,
            LocalDate rankDate,
            String rank,
            long searchCnt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new RankDto(
            id,
            rankSong,
            rankDate,
            rank,
            searchCnt,
            createdAt,
            updatedAt
        );
    }
}
