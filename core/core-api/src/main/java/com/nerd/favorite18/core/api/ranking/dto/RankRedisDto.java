package com.nerd.favorite18.core.api.ranking.dto;

import com.nerd.favorite18.storage.db.core.song.entity.SongCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankRedisDto {
    private Long songId;
    private String albumUrl;
    private String title;
    private String artist;
    private LocalDate rankDate;
    private String ranking;
    private Long searchCnt;

    public static RankRedisDto of(
            Long songId,
            String albumUrl,
            String title,
            String artist,
            LocalDate rankDate,
            String ranking,
            Long searchCnt
    ) {
        return new RankRedisDto(
                songId,
                albumUrl,
                title,
                artist,
                rankDate,
                ranking,
                searchCnt
        );
    }
}
