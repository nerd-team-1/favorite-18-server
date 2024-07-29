package com.nerd.favorite18.core.api.ranking.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import java.util.Set;

@RequiredArgsConstructor
@Repository
public class RankRedisRepository {
    private static final String SONG_KEY = "song";
    private static final String SONG_SEARCH_COUNT_KEY = "song_search_count";

    private final RedisTemplate<String, String> redisTemplate;

    public void zAddSong(String songId, String value) {
        redisTemplate.opsForHash().put(SONG_KEY, songId, value);
    }

    public Double zGetScore(String songId) {
        return redisTemplate.opsForZSet().score(SONG_SEARCH_COUNT_KEY, songId);
    }

    public void zAddScore(String songId, Double score) {
        redisTemplate.opsForZSet().add(SONG_SEARCH_COUNT_KEY, songId, score);
    }

    public String zGetValue(String songId) {
        return (String) redisTemplate.opsForHash().get(SONG_KEY, songId);
    }

    public Set<TypedTuple<String>> zGetTopScores(int count) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(SONG_SEARCH_COUNT_KEY, 0, count - 1);
    }

    public void deleteSong() {
        redisTemplate.delete(SONG_KEY);
    }

    public void deleteSongSearchCount() {
        redisTemplate.delete(SONG_SEARCH_COUNT_KEY);
    }
}
