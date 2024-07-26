package com.nerd.favorite18.core.api.ranking.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.ranking.dto.response.RankResponse;
import com.nerd.favorite18.core.api.ranking.service.RankService;
import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.dto.RankQueryDto;
import com.nerd.favorite18.storage.db.core.ranking.projection.RankListProjection;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Business
public class RankBusiness {
    private final RankService rankService;

    public List<RankResponse> getRankAll(LocalDate rankDate, MachineType machineType) {

        return rankService.getRankAll(rankDate, machineType);
    }

}
