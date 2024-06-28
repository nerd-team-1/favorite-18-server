package com.nerd.favorite18.core.api.ranking.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.ranking.business.RankBusiness;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.projection.RankListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs/rank")
@RestController
public class RankController {
    private final RankBusiness rankBusiness;

    @GetMapping
    public ApiResponse<List<RankListResponse>> getRankAll(@RequestParam LocalDate today, @RequestParam MachineType machineType) {
        List<RankListResponse> response = rankBusiness.getRankAll(today, machineType);

        return ApiResponse.success(response);
    }
}
