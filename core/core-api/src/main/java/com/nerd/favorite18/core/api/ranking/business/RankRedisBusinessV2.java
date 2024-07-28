package com.nerd.favorite18.core.api.ranking.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.ranking.dto.response.RankScoreResponse;
import com.nerd.favorite18.core.api.ranking.service.RankRedisServiceV2;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Business
public class RankRedisBusinessV2 {
    private final RankRedisServiceV2 rankRedisServiceV2;

    public void clickSong(Long songId) {
        rankRedisServiceV2.zAddScore(String.valueOf(songId));
    }

    public List<RankScoreResponse> getTop100Songs() {
        return rankRedisServiceV2.zGetTopScores(100);
    }

    public void deleteKeys() {
        rankRedisServiceV2.deleteSong();
        rankRedisServiceV2.deleteSongSearchCount();
    }
}
