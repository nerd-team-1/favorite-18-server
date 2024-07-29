package com.nerd.favorite18.core.api.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.dto.SongRankDto;
import com.nerd.favorite18.core.api.ranking.repository.RankRedisRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RankRedisServiceV2 {
    private final RankRedisRepository rankRedisRepository;

    private final ObjectMapper objectMapper;

    public void addSong(SongRankDto songRankDto) {
        try {
            String jsonValue = objectMapper.writeValueAsString(songRankDto);

            rankRedisRepository.zAddSong(songRankDto.getSongId().toString(), jsonValue);
        } catch (JsonProcessingException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "Json processing failed in zAddSong");
        }
    }

    public Double getScore(Long songId) {
        return rankRedisRepository.zGetScore(String.valueOf(songId));
    }

    public void addScore(Long songId, Double score) {
        rankRedisRepository.zAddScore(String.valueOf(songId), score);
    }

    public Optional<SongRankDto> findSongById(String songId) {
        final String jsonValue = rankRedisRepository.zGetValue(songId);

        if (ObjectUtils.isEmpty(jsonValue)) {
            return Optional.empty();
        }

        try {
            SongRankDto songRankDto = objectMapper.readValue(jsonValue, SongRankDto.class);

            return Optional.of(songRankDto);
        } catch (JsonProcessingException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "Json processing failed in findSongById");
        }
    }

    public Set<TypedTuple<String>> getTopScores(int count) {
        return rankRedisRepository.zGetTopScores(count);
    }

    public void deleteSong() {
        rankRedisRepository.deleteSong();
    }

    public void deleteSongSearchCount() {
        rankRedisRepository.deleteSongSearchCount();
    }
}
