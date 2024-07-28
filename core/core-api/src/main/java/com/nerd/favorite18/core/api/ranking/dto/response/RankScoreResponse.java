package com.nerd.favorite18.core.api.ranking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nerd.favorite18.core.api.ranking.dto.SongRankDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankScoreResponse {
    private SongRankDto songRankDto;
    private Long searchCount;

    @Builder
    public RankScoreResponse(SongRankDto songRankDto, Long searchCount) {
        this.songRankDto = songRankDto;
        this.searchCount = searchCount;
    }
}
