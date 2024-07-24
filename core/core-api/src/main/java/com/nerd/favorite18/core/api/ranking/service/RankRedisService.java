package com.nerd.favorite18.core.api.ranking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.converter.RankRedisConverter;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongCode;
import com.nerd.favorite18.storage.db.core.song.repository.SongRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankRedisService {

    private static final String REDIS_KEY_PREFIX = "rank:";
    private static final String ZSET_KEY = "rank:zset";

    private final RedisTemplate<String, String> redisTemplate;
    private final SongRepository songRepository;
    private final ObjectMapper objectMapper;
    private final RankRedisConverter rankRedisConverter;

    public List<RankRedisDto> getTop100Songs() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankSet = zSetOperations.reverseRangeWithScores(ZSET_KEY, 0, 99);

        if (rankSet == null) {
            return Collections.emptyList(); // ZSet에 데이터가 없으면 빈 리스트 반환
        }

        return rankSet.stream()
                .map(tuple -> {
                    String songJson = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + tuple.getValue());
                    if (songJson != null) {
                        RankAddRequest request = rankRedisConverter.toRequest(songJson);
                        RankRedisDto dto = rankRedisConverter.toDto(request);
                        return dto;
                    }
                    return null;
                })
                .filter(ObjectUtils::isNotEmpty) // null 값 제거
                .collect(Collectors.toList());
    }

    public Optional<Song> getSongById(Long songId) {
        return songRepository.findById(songId);
    }

    public void clickSong(Long songId, RankAddRequest songDetails) {
        String key = REDIS_KEY_PREFIX + songId;
        String songJson = redisTemplate.opsForValue().get(key);
        final Song songEntity = songRepository.findById(songId)
                .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
        System.out.println(songEntity);


        songDetails.setAlbumUrl(songEntity.getAlbumPictureUrl());
        songDetails.setTitle(songEntity.getTitle());
        songDetails.setArtist(songEntity.getArtist());
        songDetails.setSongCodes(songEntity.getSongCodes());

        if (songJson == null) {
            saveNewSongToRedis(songDetails);
        } else {
            increaseSongCntInRedis(key, songJson);
        }
    }

    public void saveNewSongToRedis(RankAddRequest songDetails) {
        try {
            String key = REDIS_KEY_PREFIX + songDetails.getSongId();
            String songJson = objectMapper.writeValueAsString(songDetails);
            redisTemplate.opsForValue().set(key, songJson, Duration.ofDays(1));

            redisTemplate.opsForZSet().add(ZSET_KEY, key, 1);
        } catch (IOException e) {
             throw new CoreApiException(ErrorType.RANK_REDIS_NOT_FOUND, e);
        }
    }

    public void increaseSongCntInRedis(String key, String songJson) {
        try {
            RankAddRequest songDetails = objectMapper.readValue(songJson, RankAddRequest.class);
            songDetails.setSearchCnt(songDetails.getSearchCnt() + 1);
            String updatedSongJson = objectMapper.writeValueAsString(songDetails);
            redisTemplate.opsForValue().set(key, updatedSongJson);

            redisTemplate.opsForZSet().incrementScore(ZSET_KEY, key, 1);
        } catch (IOException e) {
            throw new CoreApiException(ErrorType.RANK_REDIS_NOT_FOUND, e);
        }
    }
}
