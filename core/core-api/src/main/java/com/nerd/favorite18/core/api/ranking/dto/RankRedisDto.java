package com.nerd.favorite18.core.api.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankRedisDto {
    private Long songId;
    private Long searchCnt;

    public static RankRedisDto of(
            Long songId,
            Long searchCnt
    ) {
        return new RankRedisDto(
                songId,
                searchCnt
        );
    }
}
