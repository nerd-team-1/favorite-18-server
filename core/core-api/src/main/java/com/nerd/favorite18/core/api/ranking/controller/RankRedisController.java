package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.business.RankRedisBusiness;
import com.nerd.favorite18.core.api.ranking.business.RankRedisBusinessV2;
import com.nerd.favorite18.core.api.ranking.dto.response.RankScoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank/redis")
@RestController
public class RankRedisController {

    private final RankRedisBusiness rankRedisBusiness;
    private final RankRedisBusinessV2 rankRedisBusinessV2;

    /**
     * 1~100위 노래 ID 조회
     */
    @GetMapping()
    public List<RankScoreResponse> getTop100Songs() {

        return rankRedisBusinessV2.getTop100Songs();
    }

    /** 노래 상세정보 클릭 시 */
    @PostMapping("/{songId}")
    public ApiResponse<Void> clickSong(@PathVariable Long songId){
        rankRedisBusinessV2.clickSong(songId);

        return ApiResponse.success();
    }
}
