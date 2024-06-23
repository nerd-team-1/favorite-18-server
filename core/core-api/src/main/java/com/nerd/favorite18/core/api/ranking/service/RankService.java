package com.nerd.favorite18.core.api.ranking.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.converter.RankConverter;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final RankConverter rankConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public RankDto getRankAll(LocalDateTime rankDate, String machineType) {
        Rank rankEntity = rankRepository.findByRankDateOrderBySearchCntDesc(rankDate)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        return rankConverter.toDto(rankEntity);
    }

    public void increaseSearchCnt(Long songId) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        redisTemplate.opsForZSet().incrementScore("searchCnt",songId, 1);
    }

    public Long getSearchCnt(Long songId) {
        Long searchCnt = redisTemplate.opsForZSet().rank("searchCnt", songId);
        return (searchCnt != null) ? searchCnt.longValue() : 0L;
    }
}
