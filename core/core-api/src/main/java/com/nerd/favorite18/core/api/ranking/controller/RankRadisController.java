package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api.ranking.service.RedisRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs/rank")
@RestController
public class RankRadisController {

    private final RedisRankService redisRankService;

    @GetMapping("/redis/rank/{songId}")
    public String getSongCount(@PathVariable Long songId) {
        return redisRankService.getSongCounts(songId);
    }

    @PostMapping("/redis/rank")
    public void setSongCount(@RequestBody HashMap<String, String> body) {
        Long songId = Long.valueOf(body.get("songId").toString());
        String searchCnt = body.get("searchCount").toString();
        redisRankService.setSongCounts(songId, searchCnt);
    }

    @PostMapping("/redis/rank/increment/{songId}")
    public ResponseEntity<String> incrementSongCount(@PathVariable Long songId) {
        // 여기서는 간단히 조회수를 1 증가시킨다고 가정
        redisRankService.incrementSongCount(songId);
        return ResponseEntity.ok("Song count incremented for songId: " + songId);
    }
}
