package com.nerd.favorite18.core.api.ranking.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import com.nerd.favorite18.core.api.ranking.service.RankRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Business
public class RankRedisBusiness {

    private final RankRedisService rankRedisService;

    @Transactional(readOnly = true)
    public List<RankRedisDto> getTop100Songs() {
        return rankRedisService.getTop100Songs();
    }

    @Transactional
    public void clickSong(Long songId, RankAddRequest rankAddRequest) {
        rankRedisService.clickSong(songId, rankAddRequest);
    }
}
