package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import com.nerd.favorite18.core.api.ranking.service.RedisRankService;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank/redis")
@RestController
public class RankRadisController {

    private final RedisRankService redisRankService;

    /** 1~100위 노래ID 조회  */
    @GetMapping()
    public List<RankRedisDto> getTop100Songs() {
        return redisRankService.getTop100Songs();
    }

    /** 노래ID로 랭킹리스트 조회 */
    @GetMapping("/{songId}")
    public Optional<Song> getSongById(@PathVariable Long songId) {
        return redisRankService.getSongById(songId);
    }

    /** 노래 상세정보 클릭 시 */
    @PostMapping("/{songId}")
    public ApiResponse<Void> clickSong(@PathVariable Long songId, @RequestBody RankAddRequest rankAddRequest){
        redisRankService.clickSong(songId, rankAddRequest);
        return ApiResponse.success();
    }
}
