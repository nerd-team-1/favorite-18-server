package com.nerd.favorite18.core.api.ranking.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import com.nerd.favorite18.core.api.ranking.dto.response.RankResponse;
import com.nerd.favorite18.core.api.song.dto.response.SongCodeResponse;
import com.nerd.favorite18.storage.db.core.ranking.dto.RankQueryDto;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.ranking.projection.RankListProjection;
import com.nerd.favorite18.storage.db.core.song.dto.SongCodeQueryDto;
import com.nerd.favorite18.storage.db.core.song.dto.SongQueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Converter
@RequiredArgsConstructor
public class RankConverter {
    public RankResponse toRankListResponse(RankQueryDto rankQueryDto) {
        return RankResponse.builder()
                .rankId(rankQueryDto.getId())
                .title(rankQueryDto.getTitle())
                .artist(rankQueryDto.getArtist())
                .albumUrl(rankQueryDto.getAlbumUrl())
                .rankDate(rankQueryDto.getRankDate())
                .ranking(rankQueryDto.getRanking())
                .searchCnt(rankQueryDto.getSearchCnt())
                .songCode(rankQueryDto.getSongCode())
                .build();
    }
}
