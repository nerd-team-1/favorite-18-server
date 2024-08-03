package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.business.RankRedisBusiness;
import com.nerd.favorite18.core.api.ranking.business.RankRedisBusinessV2;
import com.nerd.favorite18.core.api.ranking.dto.response.RankScoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "4.2 [랭킹(Redis)]", description = "랭킹 정보(Redis)")
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
    @Operation(summary = "1~100위 노래 ID 조회", description = "1~100위 노래 ID를 조회한다.")
    @GetMapping
    public List<RankScoreResponse> getTop100Songs() {

        return rankRedisBusinessV2.getTop100Songs();
    }

    /** 노래 상세정보 클릭 시 */
    @Operation(summary = "노래 클릭", description = "노래 클릭 시 랭킹 정보를 업데이트한다.")
    @PostMapping("/{songId}")
    public ApiResponse<Void> clickSong(@PathVariable Long songId){
        rankRedisBusinessV2.clickSong(songId);

        return ApiResponse.success();
    }

    // 랭킹 키 삭제 API
    @Operation(summary = "레디스 랭킹 키 삭제", description = "레디스에 저장된 노래 캐시와 카운트 리스트를 삭제")
    @DeleteMapping
    public ApiResponse<Void> deleteKeys() {
        rankRedisBusinessV2.deleteKeys();

        return ApiResponse.success();
    }
}
