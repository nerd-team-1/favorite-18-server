package com.nerd.favorite18.core.api.ranking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisRankService {

    private final RedisTemplate redisTemplate;
    private static final String REDIS_KEY_PREFIX = "rank:songs:";

    public String getSongCounts(Long songId){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String key = REDIS_KEY_PREFIX + songId.toString();
        return values.get(key);
    }

    public void setSongCounts(Long songId, String searchCnt) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String key = REDIS_KEY_PREFIX + songId.toString();
        values.set(key, searchCnt, Duration.ofDays(1)); // 1일 뒤 메모리에서 삭제.
    }

    public void incrementSongCount(Long songId) {
        String key =  REDIS_KEY_PREFIX + songId.toString();
        redisTemplate.opsForValue().increment(key);
    }
}
