package com.nerd.favorite18.core.api.song.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.song.dto.SongDto;
import com.nerd.favorite18.core.api.song.dto.SongLikeDto;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongLike;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Converter
public class SongLikeConverter {
    public SongLike toEntity(User userEntity, Song songEntity) {

        return SongLike.builder()
                .songLikeUser(userEntity)
                .song(songEntity)
                .build();
    }

    // TODO : Song 서비스 통합 후 songEntity 를 songDto 로 변경 해야함
    public SongLikeDto toDto(SongLike entity, SongDto songDto) {

        return SongLikeDto.of(
                entity.getId(),
                songDto,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
