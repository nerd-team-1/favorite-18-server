package com.nerd.favorite18.core.api.ranking.service;

import com.nerd.favorite18.core.api.ranking.converter.RankConverter;
import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.projection.RankListResponse;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final RankConverter rankConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<RankListResponse> getRankAll(LocalDate rankDate, MachineType machineType) {
        final List<RankListResponse> ranks = rankRepository.findByRankDateAndMachineTyperOrderBySearchCntDesc(rankDate, machineType);

        return ranks;
    }

    public void increaseSearchCnt(Long songId) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        redisTemplate.opsForZSet().incrementScore("searchCnt",songId, 1);
    }
}
