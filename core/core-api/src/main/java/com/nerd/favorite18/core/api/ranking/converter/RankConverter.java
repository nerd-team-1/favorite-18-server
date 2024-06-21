package com.nerd.favorite18.core.api.ranking.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.storage.db.core.ranking.Rank;
import com.nerd.favorite18.storage.db.core.user.entity.User;

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
}
