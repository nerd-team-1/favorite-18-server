package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.service.RedisRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank/redis")
@RestController
public class RankRadisController {

    private final RedisRankService redisRankService;

    @GetMapping()
    public List<RankRedisDto> getTop100Songs() {
        return redisRankService.getTop100Songs();
    }

    @PostMapping
    public ApiResponse<Void> setSongCount(@PathVariable Long songId){
        redisRankService.setSongCount(songId);
        return ApiResponse.success();
    }

    @GetMapping("/song")
    public Optional<Song> getSongById(Long songId){
        return redisRankService.getSongById(songId);
    }
}
