package com.nerd.favorite18.core.api.ranking.service;

import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisRankService {

    private final RedisTemplate redisTemplate;
    private static final String REDIS_KEY_PREFIX = "rank:songs:";
    private static final String ZSET_KEY = "rank:songs:searchCnt";

    /** 노래별 검색 횟수 저장*/
    public void setSongCount(Long songId) {
        String key = REDIS_KEY_PREFIX + songId.toString();
        if(redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().increment(key);
        }else {
            redisTemplate.opsForValue().set(key, 1, Duration.ofDays(1));
        }
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        redisTemplate.opsForZSet().add(ZSET_KEY, key, count);
    }

    /** 1~100위 노래 조회*/
    public List<RankRedisDto> getTop100Songs(){
        ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Long>> rankSet =  zSetOperations.reverseRangeWithScores(ZSET_KEY, 0, 99);

        return rankSet.stream()
                .map(tuple -> new RankRedisDto(tuple.getValue(), tuple.getScore().longValue()))
                .collect(Collectors.toList());
    }
}
