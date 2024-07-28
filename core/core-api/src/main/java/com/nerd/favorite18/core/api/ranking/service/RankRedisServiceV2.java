package com.nerd.favorite18.core.api.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.dto.SongRankDto;
import com.nerd.favorite18.core.api.ranking.dto.response.RankScoreResponse;
import com.nerd.favorite18.core.api.song.service.SongSelectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankRedisServiceV2 {
    private final SongSelectService songSelectService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String SONG_KEY = "song";
    private static final String SONG_SEARCH_COUNT_KEY = "song_search_count";

    private void zAddSong(SongRankDto songRankDto) {
        try {
            String jsonValue = objectMapper.writeValueAsString(songRankDto);
            redisTemplate.opsForHash().put(SONG_KEY, songRankDto.getSongId().toString(), jsonValue);
        } catch (JsonProcessingException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "Json processing failed in zAddSong");
        }
    }

    public SongRankDto findSongById(String songId) {
        String jsonValue = (String) redisTemplate.opsForHash().get(SONG_KEY, songId);
        log.info(jsonValue);

        if (!ObjectUtils.isEmpty(jsonValue)) {
            try {
                return objectMapper.readValue(jsonValue, SongRankDto.class);
            } catch (JsonProcessingException e) {
                throw new CoreApiException(ErrorType.DEFAULT_ERROR, "Json processing failed in findSongById");
            }
        }

        return null;
    }

    public void zAddScore(String songId) {
        Double currentScore = redisTemplate.opsForZSet().score(SONG_SEARCH_COUNT_KEY, songId);

        if (!ObjectUtils.isEmpty(currentScore)) {

            redisTemplate.opsForZSet().add(SONG_SEARCH_COUNT_KEY, songId, currentScore + 1);
        } else {
            final SongRankDto songRankDto = songSelectService.getSongForRank(Long.valueOf(songId));
            zAddSong(songRankDto);

            redisTemplate.opsForZSet().add(SONG_SEARCH_COUNT_KEY, songId, 1);
        }
    }

    public List<RankScoreResponse> zGetTopScores(int count) {
        final Set<TypedTuple<String>> idRank = redisTemplate.opsForZSet().reverseRangeWithScores(SONG_SEARCH_COUNT_KEY, 0, count - 1);

        if (ObjectUtils.isEmpty(idRank)) {
            return List.of();
        }

        return idRank.stream().map(tuple -> {
            SongRankDto songRankDto = findSongById(tuple.getValue());
            Double originScore = tuple.getScore();
            Long score = !ObjectUtils.isEmpty(originScore) ? Math.round(originScore) : 0;

            return RankScoreResponse.builder()
                    .songRankDto(songRankDto)
                    .searchCount(score)
                    .build();
        }).collect(Collectors.toList());
    }
}
