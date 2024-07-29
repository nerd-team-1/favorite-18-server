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

    /**
     * [ 랭킹 추가 ]
     * <br/>
     * 레디스 랭킹에 노래를 추가하고, 세부페이지 클릭 수를 카운트 <br/>
     *
     * @param songId 노래 id
     */
    public void clickSong(Long songId) {
        rankRedisServiceV2.zAddScore(String.valueOf(songId));
    }

    /**
     * [ 세부페이지 클릭횟수 Top 100 ]
     * <br/>
     * 레디스 랭킹에 저장된 세부페이지 클릭 수 카운트 기준으로 리스트<br/>
     *
     * @return 랭킹 리스트
     */
    public List<RankScoreResponse> getTop100Songs() {
        return rankRedisServiceV2.zGetTopScores(100);
    }

    /**
     * [ 레디스 랭킹 삭제 ]
     * <br/>
     * 레디스에 저장된 노래 캐시와 카운트 리스트를 삭제 <br/>
     */
    public void deleteKeys() {
        rankRedisServiceV2.deleteSong();
        rankRedisServiceV2.deleteSongSearchCount();
    }
}
