package com.nerd.favorite18.core.api.song.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {
    private Long id;
    private String title;
    private String artist;
    private String albumPictureUrl;

    public static SongDto of(Long id, String title, String artist, String albumPictureUrl) {
        return new SongDto(id, title, artist, albumPictureUrl);
    }
}
