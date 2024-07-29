package com.nerd.favorite18.core.api.ranking.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.dto.SongRankDto;
import com.nerd.favorite18.core.api.ranking.dto.response.RankScoreResponse;
import com.nerd.favorite18.core.api.ranking.service.RankRedisServiceV2;
import com.nerd.favorite18.core.api.song.service.SongSelectService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class RankRedisBusinessV2 {
    private final RankRedisServiceV2 rankRedisServiceV2;
    private final SongSelectService songSelectService;

    /**
     * [ 랭킹 추가 ]
     * <br/>
     * 레디스 랭킹에 노래를 추가하고, 세부페이지 클릭 수를 카운트<br/>
     *
     * @param songId 노래 id
     */
    public void clickSong(Long songId) {
        final Double currentScore = rankRedisServiceV2.getScore(songId);

        if (!ObjectUtils.isEmpty(currentScore)) {
            rankRedisServiceV2.addScore(songId, currentScore + 1);
        } else {
            final SongRankDto songRankDto = songSelectService.getSongForRank(songId);
            rankRedisServiceV2.addSong(songRankDto);

            rankRedisServiceV2.addScore(songId, (double) 1);
        }
    }

    /**
     * [ 세부페이지 클릭횟수 Top 100 ]
     * <br/>
     * 레디스 랭킹에 저장된 세부페이지 클릭 수 카운트 기준으로 리스트<br/>
     *
     * @return 랭킹 리스트
     */
    public List<RankScoreResponse> getTop100Songs() {
        final Set<ZSetOperations.TypedTuple<String>> idRank = rankRedisServiceV2.getTopScores(100);

        if (ObjectUtils.isEmpty(idRank)) {
            throw new CoreApiException(ErrorType.RANK_REDIS_NOT_FOUND);
        }

        return idRank.stream().map(tuple -> {
            SongRankDto songRankDto = rankRedisServiceV2.findSongById(tuple.getValue());
            Double originScore = tuple.getScore();
            Long score = !ObjectUtils.isEmpty(originScore) ? Math.round(originScore) : 0;

            return RankScoreResponse.builder()
                    .songRankDto(songRankDto)
                    .searchCount(score)
                    .build();
        }).collect(Collectors.toList());
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
