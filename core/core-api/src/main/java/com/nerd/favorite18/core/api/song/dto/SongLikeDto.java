package com.nerd.favorite18.core.api.song.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongLikeDto {
    private Long id;
    private SongDto song;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SongLikeDto of(Long id, SongDto song, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new SongLikeDto(id, song, createdAt, updatedAt);
    }
}
