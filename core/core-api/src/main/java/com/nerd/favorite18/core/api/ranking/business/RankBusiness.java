package com.nerd.favorite18.core.api.ranking.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.api.ranking.service.RankService;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Business
public class RankBusiness {
    private final RankService rankService;

    public RankDto getRankAll(LocalDateTime rankDate, String machineType) {

        return rankService.getRankAll(rankDate, machineType);
    }

}
