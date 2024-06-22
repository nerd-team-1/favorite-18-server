package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.business.RankBusiness;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs/rank")
@RestController
public class RankController {
    private final RankBusiness rankBusiness;

    @GetMapping
    public ApiResponse<RankDto> getRankAll(@PathVariable LocalDateTime today, String machineType) {
        RankDto response = rankBusiness.getRankAll(today, machineType);

        return ApiResponse.success(response);
    }


}
