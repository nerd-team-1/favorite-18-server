package com.nerd.favorite18.core.api.ranking.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;

import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RankConverter {
    public RankDto toDto(Rank entity) {
        return RankDto.of(
                entity.getId(),
                entity.getRankSong(),
                entity.getRankDate(),
                entity.getRank(),
                entity.getSearchCnt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    public List<RankDto> toDto(List<Rank> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
