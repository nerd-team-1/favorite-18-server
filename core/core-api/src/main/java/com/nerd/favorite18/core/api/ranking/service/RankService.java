package com.nerd.favorite18.core.api.ranking.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.converter.RankConverter;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final RankConverter rankConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<RankDto> getRankAll(LocalDate rankDate, MachineType machineType) {
        List<Rank> rankEntities = rankRepository.findByRankDateAndMachineTyperOrderBySearchCntDesc(rankDate, machineType)
                .orElseThrow(() -> new CoreApiException(ErrorType.NULL_POINT));

        return rankConverter.toDto(rankEntities);
    }

    public void increaseSearchCnt(Long songId) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        redisTemplate.opsForZSet().incrementScore("searchCnt",songId, 1);
    }
}
