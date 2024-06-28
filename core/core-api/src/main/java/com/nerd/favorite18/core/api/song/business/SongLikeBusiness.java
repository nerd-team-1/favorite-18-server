package com.nerd.favorite18.core.api.song.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.song.dto.SongLikeDto;
import com.nerd.favorite18.core.api.song.service.SongLikeService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Business
public class SongLikeBusiness {
    private final SongLikeService songLikeService;

    public Page<SongLikeDto> myLikeList(UserDto userDto, Pageable pageable) {
        return songLikeService.getMySongLikeList(userDto, pageable);
    }

    public void like(UserDto userDto, Long songId) {
        songLikeService.insertSongLike(userDto, songId);
    }

    public void unLike(UserDto userDto, Long songId) {
        songLikeService.deleteSongLike(userDto, songId);
    }
}
