package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.business.RankRedisBusiness;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import com.nerd.favorite18.core.api.song.business.SongBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank/redis")
@RestController
public class RankRadisController {

    private final RankRedisBusiness rankRedisBusiness;
    private final SongBusiness songBusiness;

    /** 1~100위 노래ID 조회  */
    @GetMapping()
    public List<RankRedisDto> getTop100Songs() {
        return rankRedisBusiness.getTop100Songs();
    }

    /** 노래 상세정보 클릭 시 */
    @PostMapping("/{songId}")
    public ApiResponse<Void> clickSong(@PathVariable Long songId, @RequestBody RankAddRequest rankAddRequest){
        rankRedisBusiness.clickSong(songId, rankAddRequest);
        return ApiResponse.success();
    }
}
