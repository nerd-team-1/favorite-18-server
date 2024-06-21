package com.nerd.favorite18.core.api.ranking.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.converter.RankConverter;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.api.user.converter.UserConverter;
import com.nerd.favorite18.storage.db.core.ranking.Rank;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final RankConverter rankConverter;

  /*  @Transactional(readOnly = true)
    public RankDto getRankAll(Date rankDate, String machineType) {
        Rank rankEntity = rankRepository.findByRankDateOrderBySearchCntDesc(rankDate)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        return RankConverter.toDto(rankEntity);
    }*/
}
